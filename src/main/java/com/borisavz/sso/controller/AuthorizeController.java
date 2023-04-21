package com.borisavz.sso.controller;

import com.borisavz.sso.dto.TokenResponseDTO;
import com.borisavz.sso.dto.LoginFormDTO;
import com.borisavz.sso.entity.SavedLogin;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/authorize")
    public String getLoginPage(
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("service") String service,
            @CookieValue(value = "sso_token", required = false, defaultValue = "") String ssoToken,
            HttpServletResponse response,
            Model model
    ) {
        if(ssoToken.isEmpty()) {
            response.addCookie(new Cookie("sso_token", UUID.randomUUID().toString()));

            model.addAttribute("savedLogins", new ArrayList<SavedLogin>());
        } else {
            List<SavedLogin> savedLogins = oAuthService.getValidSavedLogins(ssoToken);

            model.addAttribute("savedLogins", savedLogins);
        }

        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("service", service);

        return "login";
    }

    @GetMapping("/saved/{username}")
    public String loginSavedLogin(
            @PathVariable String username,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("service") String service,
            @CookieValue("sso_token") String ssoToken,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        TokenResponseDTO responseDTO = oAuthService.generateServiceTokenFromSsoToken(ssoToken, username, service);

        if(!responseDTO.isSsoLoginSuccessful()) {
            redirectAttributes.addAttribute("redirect_uri", redirectUri);
            redirectAttributes.addAttribute("service", service);

            return "redirect://localhost:8080/authorize";
        }

        if(!responseDTO.isServiceLoginSuccessful())
            return "no_service_role";

        return "redirect://" + redirectUri
                + "#access_token="
                + URLEncoder.encode(responseDTO.getAccessToken(), StandardCharsets.UTF_8);
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginFormDTO loginFormDTO,
            @CookieValue("sso_token") String ssoToken,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ) {
        TokenResponseDTO responseDTO = oAuthService.generateTokenImplicitGrant(loginFormDTO, ssoToken);

        if(!responseDTO.isSsoLoginSuccessful()) {
            redirectAttributes.addAttribute("redirect_uri", loginFormDTO.getRedirectUri());
            redirectAttributes.addAttribute("service", loginFormDTO.getService());

            return "redirect://localhost:8080/authorize";
        }

        if(!responseDTO.isServiceLoginSuccessful()) {
            return "no_service_role";
        }

        return "redirect://" + loginFormDTO.getRedirectUri()
                + "#access_token="
                + URLEncoder.encode(responseDTO.getAccessToken(), StandardCharsets.UTF_8);
    }
}
