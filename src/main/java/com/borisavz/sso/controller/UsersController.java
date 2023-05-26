package com.borisavz.sso.controller;

import com.borisavz.sso.entity.ServiceRole;
import com.borisavz.sso.entity.User;
import com.borisavz.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public User getUser(
            @PathVariable String username
    ) {
        return userService.getUser(username);
    }

    @PutMapping("/{username}/serviceRoles")
    public void addUserServiceRole(
            @PathVariable String username,
            @RequestBody ServiceRole serviceRole
    ) {
        userService.addUserServiceRole(username, serviceRole);
    }
}
