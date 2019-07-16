package com.gangoffours.pprs.pprs.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.common.InvalidOperationException;
import com.gangoffours.pprs.pprs.common.ResourceNotFoundException;
import com.gangoffours.pprs.pprs.models.User;
import com.gangoffours.pprs.pprs.repositories.IRoleRepository;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import com.gangoffours.pprs.pprs.viewmodels.RegistrationForm;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends AuthControllerBase {
    private IUserRepository _userRepository;
    private PasswordEncoder _passwordEncoder;
    private IRoleRepository _roleRepository;

    public UserAdminController(IUserRepository userRepository, IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        super(userRepository);
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
        _roleRepository = roleRepository;
    }

    @GetMapping()
    public String getUserIndex(Model model) {
        model.addAttribute("users", _userRepository.findAll());
        return "admin/user/index";
    }

    @GetMapping("/register")
    public String getUserRegister(Model model) {
        model.addAttribute("form", new RegistrationForm());
        model.addAttribute("selectroles", _roleRepository.findAll());

        return "admin/user/register";
    }

    @PostMapping("/register")
    public String postUserRegister(
        @ModelAttribute("form") @Valid RegistrationForm form,
        BindingResult bindingResult,
        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("selectroles", _roleRepository.findAll());
            return "admin/user/register";
        }

        for (User user : _userRepository.findAll()) {
            if (user.getFullname().equals(form.getFullname())) {
                model.addAttribute("selectroles", _roleRepository.findAll());
                model.addAttribute("duplicated", "fullname");
                return "admin/user/register";
            }
        }

        for (User user : _userRepository.findAll()) {
            if (user.getUsername().equals(form.getUser_name())) {
                model.addAttribute("selectroles", _roleRepository.findAll());
                model.addAttribute("duplicated", "username");
                return "admin/user/register";
            }
        }

        _userRepository.save(form.toUser(_passwordEncoder, _roleRepository));
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable(name = "id") long id, Authentication authentication) throws Exception {
        if (this.getUser(authentication).getId() == id)
        {
            throw new InvalidOperationException("Cannot delete yourself");
        }

        Optional<User> deleteUser = _userRepository.findById(id);

        if (!deleteUser.isPresent())
        {
            throw new ResourceNotFoundException();
        }

        deleteUser.ifPresent(u -> _userRepository.deleteById(u.getId()));
        return "redirect:/admin/user";
    }
}