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
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ServiceRole> serviceRoles;
}
