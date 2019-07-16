package com.gangoffours.pprs.pprs.common;

import com.gangoffours.pprs.pprs.models.User;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AuthControllerBase {
    private IUserRepository _userRepository;

    public AuthControllerBase(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @ModelAttribute("_csrf")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }

    // Set the PageTitle for the controller
    @ModelAttribute("User")
    public User getUser(Authentication authentication) {
        return authentication != null ? _userRepository.findByUsername(authentication.getName()) : null;
    }
}