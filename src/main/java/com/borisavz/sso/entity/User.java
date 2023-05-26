package com.borisavz.sso.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    private List<ServiceRole> serviceRoles;

    private String fatherJmbg;
    private String motherJmbg;
}
