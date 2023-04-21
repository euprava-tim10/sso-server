package com.borisavz.sso.repository;

import com.borisavz.sso.entity.SavedLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedLoginRepository extends JpaRepository<SavedLogin, Long> {

    List<SavedLogin> getBySsoToken(String ssoToken);

    Optional<SavedLogin> findBySsoTokenAndUsername(String ssoToken, String username);

    void deleteBySsoTokenAndUsername(String ssoToken, String username);
}
