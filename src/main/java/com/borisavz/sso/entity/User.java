package com.borisavz.sso.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ServiceRole> serviceRoles;

    private String fatherJmbg;
    private String motherJmbg;
}
