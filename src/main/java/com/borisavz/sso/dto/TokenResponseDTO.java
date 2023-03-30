package com.borisavz.sso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {

    private String ssoToken;
    private boolean ssoLoginSuccessful;

    private String accessToken;
    private boolean serviceLoginSuccessful;
}
