package com.borisavz.sso.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id" , columnDefinition="VARCHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ClientRegistration clientRegistration;
}
