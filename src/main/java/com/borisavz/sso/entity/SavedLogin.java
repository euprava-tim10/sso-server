package com.borisavz.sso.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SavedLogin {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    private String ssoToken;
    private Date expiresAt;

    public boolean isValid() {
        return (new Date()).before(expiresAt);
    }
}
