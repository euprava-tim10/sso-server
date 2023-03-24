package com.borisavz.sso.entity;

import com.borisavz.sso.converter.StringListConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ClientRegistration {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "uuid", strategy = "guid")
    @Column(name = "id" , columnDefinition="VARCHAR(36)")
    private UUID clientId;

    private String registrationAccessToken;

    private String clientName;

    @Convert(converter = StringListConverter.class)
    private List<String> redirectUris;

    private String clientSecret;

    private String allowedScope;

    public boolean isRequestedScopeAllowed(String requestedScope) {
        if("create_post".equals(requestedScope))
            return true;

        return requestedScope.equals(allowedScope);
    }

    public String getMinScope(String requestedScope) {
        if("create_post".equals(requestedScope))
            return requestedScope;

        return allowedScope;
    }
}
