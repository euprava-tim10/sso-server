package com.borisavz.sso.entity;

import com.borisavz.sso.converter.HashMapConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
public class ServiceRole {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String service;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> attributes;
}
