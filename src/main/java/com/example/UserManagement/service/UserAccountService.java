package com.example.UserManagement.service;

import com.example.UserManagement.domain.UserAccount;
import com.example.UserManagement.repos.RoleRepo;
import com.example.UserManagement.repos.UserAccountRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserAccountService implements UserDetailsService {
    UserAccountRepo userAccountRepo;
    RoleRepo roleRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepo userAccountRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userAccountRepo = userAccountRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepo.findByUsername(username);
    }

    public void addUserAccount(UserAccount userAccount,
                               String userRoles) {

        userAccount = new UserAccount(userAccount.getUsername(), passwordEncoder.encode(userAccount.getPassword()), userAccount.getFirstName(),
                userAccount.getLastName(), userAccount.isActive(), roleRepo.findByName(userRoles));
        userAccountRepo.save(userAccount);
    }

    public void editUserAccount(UserAccount userAccount, String userRoles) {
        userAccount.setUsername(userAccount.getUsername());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setFirstName(userAccount.getFirstName());
        userAccount.setLastName(userAccount.getLastName());
        userAccount.setRoles(roleRepo.findByName(userRoles));
        userAccount.setActive(userAccount.isActive());
        userAccountRepo.save(userAccount);
    }
}
