package com.borisavz.sso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    private boolean ssoLoginSuccessful;

    private String accessToken;
    private boolean serviceLoginSuccessful;
}
