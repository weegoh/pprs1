package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomeController extends AuthControllerBase {
    
    public HomeController(IUserRepository userRepository) {
        super(userRepository);
    }

    @GetMapping()
    public String getIndex(Model model, Authentication authentication) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Home");

        return (authentication == null || !authentication.isAuthenticated())
            ? "home/index" : "home/user";
    }

    @GetMapping("/login")
    public String getSignIn(Model model) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Sign In");
        return "home/login";
    }
}