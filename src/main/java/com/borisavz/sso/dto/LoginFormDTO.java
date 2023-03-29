package com.borisavz.sso.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFormDTO {

    private String username;
    private String password;

    private String redirectUri;
    private String service;
}
