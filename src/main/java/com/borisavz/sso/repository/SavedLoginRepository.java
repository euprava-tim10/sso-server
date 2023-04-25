package com.borisavz.sso.repository;

import com.borisavz.sso.entity.SavedLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SavedLoginRepository extends JpaRepository<SavedLogin, Long> {

    List<SavedLogin> getBySsoToken(String ssoToken);

    @Query("SELECT sl FROM SavedLogin sl " +
            "WHERE sl.ssoToken = ?1 " +
            "AND sl.user.username = ?2")
    Optional<SavedLogin> findBySsoTokenAndUsername(String ssoToken, String username);

    @Modifying
    @Query("DELETE FROM SavedLogin sl " +
            "WHERE sl.ssoToken = ?1 " +
            "AND sl.user.username = ?2")
    void deleteBySsoTokenAndUsername(String ssoToken, String username);
}
