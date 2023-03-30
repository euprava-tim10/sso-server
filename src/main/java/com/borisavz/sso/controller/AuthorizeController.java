package com.borisavz.sso.controller;

import com.borisavz.sso.dto.TokenResponseDTO;
import com.borisavz.sso.dto.LoginFormDTO;
import com.borisavz.sso.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthorizeController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/authorize")
    public String getLoginPage(
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("service") String service,
            @CookieValue(value = "sso_token", required = false, defaultValue = "") String ssoToken,
            Model model
    ) {
        if(!ssoToken.isEmpty()) {
            TokenResponseDTO responseDTO = oAuthService.generateServiceTokenFromSsoToken(ssoToken, service);

            if(responseDTO.isSsoLoginSuccessful()) {
                if(!responseDTO.isServiceLoginSuccessful())
                    return "no_service_role";

                return "redirect://" + redirectUri
                        + "#access_token="
                        + URLEncoder.encode(responseDTO.getAccessToken(), StandardCharsets.UTF_8);
            }
        }

        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("service", service);

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginFormDTO loginFormDTO,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ) {
        TokenResponseDTO responseDTO = oAuthService.generateTokenImplicitGrant(loginFormDTO);

        if(!responseDTO.isSsoLoginSuccessful()) {
            redirectAttributes.addAttribute("redirect_uri", loginFormDTO.getRedirectUri());
            redirectAttributes.addAttribute("service", loginFormDTO.getService());

            return "redirect://localhost:8080/authorize";
        }

        response.addCookie(new Cookie("sso_token", responseDTO.getSsoToken()));

        if(!responseDTO.isServiceLoginSuccessful()) {
            return "no_service_role";
        }

        return "redirect://" + loginFormDTO.getRedirectUri()
                + "#access_token="
                + URLEncoder.encode(responseDTO.getAccessToken(), StandardCharsets.UTF_8);
    }
}
