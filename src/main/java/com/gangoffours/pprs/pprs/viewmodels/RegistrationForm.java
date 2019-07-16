package com.gangoffours.pprs.pprs.viewmodels;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.gangoffours.pprs.pprs.models.Role;
import com.gangoffours.pprs.pprs.models.User;
import com.gangoffours.pprs.pprs.repositories.IRoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {

    @NotEmpty(message = "Username cannot be empty")
    private String user_name;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 10, message = "Password must be a minimum of 10 characters")
    private String password;
    @NotEmpty(message = "The user's full name cannot be empty")
    private String fullname;
    private Collection<String> roles;

    public User toUser(PasswordEncoder passwordEncoder, IRoleRepository roleRepository) {
        User user = new User(
            user_name,
            passwordEncoder.encode(password),
            fullname
        );

        Collection<Role> roleEntities = roles
            .stream().map(r -> roleRepository.findByName(r))
            .collect(Collectors.toList());

        user.setRoles(roleEntities);

        return user;
    }
}