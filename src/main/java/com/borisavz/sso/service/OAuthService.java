package com.borisavz.sso.service;

import com.borisavz.sso.dto.*;
import com.borisavz.sso.entity.ServiceRole;
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

    public TokenResponseDTO generateTokenImplicitGrant(LoginFormDTO loginFormDTO) {
        User user = userRepository.findByUsername(loginFormDTO.getUsername());

        if(!passwordEncoder.matches(loginFormDTO.getPassword(), user.getPassword())) {
            return TokenResponseDTO.builder()
                    .ssoLoginSuccessful(false)
                    .serviceLoginSuccessful(false)
                    .build();
        }

        Map<String, Object> ssoClaims = new HashMap<>();

        ssoClaims.put("username",loginFormDTO.getUsername());
        ssoClaims.put("service", "SSO");

        String ssoTokenString = Jwts.builder()
                .setClaims(ssoClaims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(oneWeekFromNow())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        Optional<ServiceRole> optionalServiceRole = user.getServiceRoles().stream()
                .filter(r -> r.getService().equals(loginFormDTO.getService()))
                .findFirst();

        if(optionalServiceRole.isEmpty()) {
            return TokenResponseDTO.builder()
                    .ssoToken(ssoTokenString)
                    .ssoLoginSuccessful(true)
                    .serviceLoginSuccessful(false)
                    .build();
        }

        ServiceRole serviceRole = optionalServiceRole.get();

        Map<String, Object> serviceClaims = new HashMap<>();

        serviceClaims.put("username",loginFormDTO.getUsername());
        serviceClaims.put("service", loginFormDTO.getService());
        serviceClaims.put("role", serviceRole.getRole());
        serviceClaims.putAll(serviceRole.getAttributes());

        String tokenString = Jwts.builder()
                .setClaims(serviceClaims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(oneWeekFromNow())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return TokenResponseDTO.builder()
                .ssoToken(ssoTokenString)
                .ssoLoginSuccessful(true)
                .accessToken(tokenString)
                .serviceLoginSuccessful(true)
                .build();
    }

    public TokenResponseDTO generateServiceTokenFromSsoToken(String ssoToken, String service) {
        if(!validateToken(ssoToken)) {
            return TokenResponseDTO.builder()
                    .ssoLoginSuccessful(false)
                    .build();
        }

        User user = userRepository.findByUsername(getUsername(ssoToken));

        Optional<ServiceRole> optionalServiceRole = user.getServiceRoles().stream()
                .filter(r -> r.getService().equals(service))
                .findFirst();

        if(optionalServiceRole.isEmpty()) {
            return TokenResponseDTO.builder()
                    .ssoLoginSuccessful(true)
                    .serviceLoginSuccessful(false)
                    .build();
        }

        ServiceRole serviceRole = optionalServiceRole.get();

        Map<String, Object> serviceClaims = new HashMap<>();

        serviceClaims.put("username",user.getUsername());
        serviceClaims.put("service", service);
        serviceClaims.put("role", serviceRole.getRole());
        serviceClaims.putAll(serviceRole.getAttributes());

        String tokenString = Jwts.builder()
                .setClaims(serviceClaims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(oneWeekFromNow())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return TokenResponseDTO.builder()
                .accessToken(tokenString)
                .serviceLoginSuccessful(true)
                .ssoLoginSuccessful(true)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("username");
    }

    private Date oneWeekFromNow() {
        return new Date(System.currentTimeMillis() + ONE_WEEK_MILLISECONDS);
    }
}
