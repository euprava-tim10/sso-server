package com.borisavz.sso.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CodeGrant {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "uuid", strategy = "guid")
    @Column(name = "id" , columnDefinition="VARCHAR(36)")
    private UUID id;

    @Column
    private String username;

    private String scope;

    private Date issuedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private ClientRegistration clientRegistration;
}
