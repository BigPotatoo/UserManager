package com.example.UserManagement.repos;

import com.example.UserManagement.domain.Role;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(@NonNull String name);
    boolean existsByName(@NonNull String name);
}
