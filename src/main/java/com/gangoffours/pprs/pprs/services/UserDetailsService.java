package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import java.util.stream.Collectors;

import com.gangoffours.pprs.pprs.models.Role;
import com.gangoffours.pprs.pprs.models.User;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private IUserRepository _userRepository;

    @Autowired
    public UserDetailsService(IUserRepository userRepo) {
        _userRepository = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = _userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public final Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles
            .stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toList());
    }
}