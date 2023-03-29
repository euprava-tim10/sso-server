package com.borisavz.sso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {

    private String accessToken;
}
