package com.gangoffours.pprs.pprs;

import java.util.Arrays;

import com.gangoffours.pprs.pprs.models.Role;
import com.gangoffours.pprs.pprs.models.User;
import com.gangoffours.pprs.pprs.repositories.IRoleRepository;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "Admin");
        Role doctorRole = createRoleIfNotFound("ROLE_DOCTOR", "Doctor");
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "Manager");

        // Create default administrator user
        User user = new User(
            "administrator",
            passwordEncoder.encode("password"),
            "Administrator"
        );
        
        user.setRoles(Arrays.asList(adminRole, doctorRole, managerRole));
        //userRepository.save(user);
        upsertUser(user);
        alreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, String description) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name, description);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private User upsertUser(User user) {
        User existing = userRepository.findByUsername(user.getUsername());

        if (existing == null) {
            userRepository.save(user);
        }
        else {
            user.setId(existing.getId());
            userRepository.save(user);
        }

        return user;
    }

}