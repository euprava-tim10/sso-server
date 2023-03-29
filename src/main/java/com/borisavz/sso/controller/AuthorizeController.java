package com.borisavz.sso.controller;

import com.borisavz.sso.dto.ImplicitGrantTokenResponseDTO;
import com.borisavz.sso.dto.LoginFormDTO;
import com.borisavz.sso.exception.InvalidClientException;
import com.borisavz.sso.exception.InvalidRequestException;
import com.borisavz.sso.exception.InvalidScopeException;
import com.borisavz.sso.exception.InvalidUserCredentialsException;
import com.borisavz.sso.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("service", service);

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginFormDTO loginFormDTO,
            RedirectAttributes redirectAttributes
    ) throws InvalidClientException, InvalidRequestException, InvalidScopeException {
        try {
            ImplicitGrantTokenResponseDTO responseDTO = oAuthService.generateTokenImplicitGrant(loginFormDTO);

            String hash = "#access_token=" + URLEncoder.encode(responseDTO.getAccessToken(), StandardCharsets.UTF_8)
                    + "&token_type=" + URLEncoder.encode(responseDTO.getTokenType(), StandardCharsets.UTF_8)
                    + "&expires_in=" + responseDTO.getExpiresIn()
                    + "&scope=" + URLEncoder.encode(responseDTO.getScope(), StandardCharsets.UTF_8);

            return "redirect://" + loginFormDTO.getRedirectUri() + hash;
        } catch (InvalidUserCredentialsException e) {
            redirectAttributes.addAttribute("redirect_uri", loginFormDTO.getRedirectUri());
            redirectAttributes.addAttribute("service", loginFormDTO.getService());

            return "redirect://localhost:8080/authorize";
        }
    }
}
