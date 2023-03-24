package com.borisavz.sso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImplicitGrantTokenResponseDTO {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String scope;
}
