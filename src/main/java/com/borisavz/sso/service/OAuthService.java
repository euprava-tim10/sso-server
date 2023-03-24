package com.borisavz.sso.service;

import com.borisavz.sso.dto.*;
import com.borisavz.sso.entity.User;
import com.borisavz.sso.exception.*;
import com.borisavz.sso.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class OAuthService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "borisavz.com";
    
    private static final long ONE_WEEK_SECONDS = 7 * 24 * 60 * 60;
    private static final long ONE_WEEK_MILLISECONDS = ONE_WEEK_SECONDS * 1000;

    public ImplicitGrantTokenResponseDTO generateTokenImplicitGrant(LoginFormDTO loginFormDTO) throws InvalidClientException, InvalidRequestException, InvalidUserCredentialsException {

        UUID tokenId = UUID.randomUUID();

        Map<String, Object> claims = new HashMap<>();

        User user = userRepository.findByUsername(loginFormDTO.getUsername());

        if(!passwordEncoder.matches(loginFormDTO.getPassword(), user.getPassword()))
            throw new InvalidUserCredentialsException();

        claims.put("username",loginFormDTO.getUsername());
        //claims.put("scope", clientRegistration.getMinScope(loginFormDTO.getScope()));
        claims.put("token_id", tokenId.toString());

        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(oneWeekFromNow())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return ImplicitGrantTokenResponseDTO.builder()
                .accessToken(tokenString)
                .tokenType("bearer")
                .expiresIn(ONE_WEEK_SECONDS)
                //.scope(clientRegistration.getAllowedScope())
                .build();
    }

    private Date oneWeekFromNow() {
        return new Date(System.currentTimeMillis() + ONE_WEEK_MILLISECONDS);
    }

    private Date tenMinutesBeforeNow() {
        return new Date(System.currentTimeMillis() - 10 * 60 * 1000);
    }
}
