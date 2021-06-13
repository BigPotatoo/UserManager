package com.example.UserManagement.controller;

import com.example.UserManagement.domain.UserAccount;
import com.example.UserManagement.repos.RoleRepo;
import com.example.UserManagement.repos.UserAccountRepo;
import com.example.UserManagement.service.UserAccountService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegistrationController {
    UserAccountRepo userAccountRepo;
    RoleRepo roleRepo;
    UserAccountService userAccountService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping
    public String base() {
        return "redirect:/user";
    }

    @GetMapping("/user/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String registrationView(Model model) {
        model.addAttribute("roles", roleRepo.findAll());
        return "registration";
    }

    @PostMapping("/user/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String registration(@Valid UserAccount userAccount,
                               BindingResult bindingResult,
                               @RequestParam String userRoles,
                               Model model) {
        Map<String, List<String>> errorsMap = ControllerUtils.getErrors(bindingResult);
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(errorsMap);
        }
        model.addAttribute("roles", roleRepo.findAll());
        if (userAccountRepo.findByUsername(userAccount.getUsername()) != null) {
            model.addAttribute("usernameEx", "User Account exists!");
            return "registration";
        } else if(userAccountRepo.findByUsername(userAccount.getUsername()) == null) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("usernameEx", "");
                return "registration";
            }
        }
        userAccountService.addUserAccount(userAccount, userRoles);
        return "redirect:/user";
    }
}