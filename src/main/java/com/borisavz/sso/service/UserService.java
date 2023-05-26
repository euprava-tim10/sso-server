package com.borisavz.sso.service;

import com.borisavz.sso.entity.ServiceRole;
import com.borisavz.sso.entity.User;
import com.borisavz.sso.repository.ServiceRoleRepository;
import com.borisavz.sso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRoleRepository serviceRoleRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public void addUserServiceRole(String username, ServiceRole serviceRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Optional<ServiceRole> optionalCurrent = user.getServiceRoles().stream()
                .filter(r -> r.getService().equals(serviceRole.getService()))
                .findFirst();

        if(optionalCurrent.isPresent()) {
            ServiceRole current = optionalCurrent.get();

            user.getServiceRoles().remove(current);
            serviceRoleRepository.delete(current);
        }

        user.getServiceRoles().add(serviceRole);

        serviceRoleRepository.save(serviceRole);
        userRepository.save(user);
    }
}
