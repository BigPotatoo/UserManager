package com.example.UserManagement.service;

import com.example.UserManagement.domain.Role;
import com.example.UserManagement.domain.UserAccount;
import com.example.UserManagement.repos.RoleRepo;
import com.example.UserManagement.repos.UserAccountRepo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DBInitializer {
    RoleRepo roleRepo;
    UserAccountRepo userAccountRepo;
    PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        Role role1 = new Role(1L,"USER");
        Role role2 = new Role(2L,"ADMIN");
        UserAccount admin = new UserAccount("ADMIN", passwordEncoder.encode("ADMIN"),"ADMIN",
                "ADMIN",true,role2);

        if (!roleRepo.existsByName(role1.getName())) {
            roleRepo.save(role1);
        }
        if (!roleRepo.existsByName(role2.getName())) {
            roleRepo.save(role2);
        }
        if(!userAccountRepo.existsByUsername(admin.getUsername())){
            userAccountRepo.save(admin);
        }
    }

}
