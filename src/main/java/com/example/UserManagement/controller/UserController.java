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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/user")
public class UserController {
    UserAccountRepo userAccountRepo;
    RoleRepo roleRepo;
    UserAccountService userAccountService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String userList(Model model) {
        model.addAttribute("users", userAccountRepo.findAll());
        model.addAttribute("roles", roleRepo.findAll());
        return "userList";
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String userDetails(@PathVariable Long id,
                              Model model) throws Exception {
        UserAccount userAccount = userAccountRepo.findById(id).orElseThrow(Exception::new);
        model.addAttribute("active", userAccount.isActive());
        return "userDetails";
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userDetailsActiveEdit(@PathVariable Long id,
                                        @RequestParam boolean active) throws Exception {
        UserAccount userAccount = userAccountRepo.findById(id).orElseThrow(Exception::new);
        userAccount.setActive(active);
        userAccountRepo.save(userAccount);
        return "redirect:/user";
    }

    @GetMapping("{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditView(@PathVariable Long id, Model model) throws Exception {
        UserAccount userAccount = userAccountRepo.findById(id).orElseThrow(Exception::new);
        userAccountAddAttributeToModel(userAccount, model);
        return "userEdit";
    }

    @PostMapping("{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(@Valid UserAccount userAccount,
                           BindingResult bindingResult,
                           @RequestParam String userRoles,
                           Model model) {
        userAccountAddAttributeToModel(userAccount, model);
        Map<String, List<String>> errorsMap = ControllerUtils.getErrors(bindingResult);
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(errorsMap);
        }
        model.addAttribute("roles", roleRepo.findAll());
        if (bindingResult.hasErrors()) {
            model.addAttribute("usernameEx", "");
            return "userEdit";
        }
        UserAccount userAccountAFromDB = userAccountRepo.findByUsername(userAccount.getUsername());
        if(userAccountAFromDB == null) {
            userAccountService.editUserAccount(userAccount, userRoles);
        } else if (userAccountAFromDB.getId().equals(userAccount.getId()))
            userAccountService.editUserAccount(userAccount, userRoles);
        else {
            model.addAttribute("usernameEx", "User Account exists!");
            return "userEdit";
        }
        return "redirect:/user";
    }

    private void userAccountAddAttributeToModel(@Valid UserAccount userAccount, Model model) {
        model.addAttribute("username", userAccount.getUsername());
        model.addAttribute("password", userAccount.getPassword());
        model.addAttribute("firstName", userAccount.getFirstName());
        model.addAttribute("lastName", userAccount.getLastName());
        model.addAttribute("userRoles", roleRepo.findAll());
        model.addAttribute("active", userAccount.isActive());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/filterByUsername")
    public String filterByUsername(@RequestParam String filterByUsername, Model model) {
        Iterable<UserAccount> userAccounts;
        if (filterByUsername != null && !filterByUsername.isEmpty())
            userAccounts = userAccountRepo.findAllByUsername(filterByUsername);
        else
            userAccounts = userAccountRepo.findAll();

        model.addAttribute("users", userAccounts);
        model.addAttribute("roles", roleRepo.findAll());
        return "userList";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/filterByRole")
    public String filterByRole(@RequestParam Long filterByRole, Model model) {
        Iterable<UserAccount> userAccounts;
        if (filterByRole != 0)
            userAccounts = userAccountRepo.findAllByRolesId(filterByRole);
        else
            userAccounts = userAccountRepo.findAll();

        model.addAttribute("users", userAccounts);
        model.addAttribute("roles", roleRepo.findAll());
        return "userList";
    }
}