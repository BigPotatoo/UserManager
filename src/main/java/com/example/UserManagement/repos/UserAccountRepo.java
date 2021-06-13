package com.example.UserManagement.repos;

import com.example.UserManagement.domain.UserAccount;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(@NonNull String username);
    List<UserAccount> findAllByUsername(@NonNull String username);
    @Query(value = "SELECT * FROM user_account\n" +
            "join \"role\"\n" +
            "on user_account.roles_id = \"role\".id\n" +
            "where user_account.roles_id = :#{#roles}",
            nativeQuery = true)
    List<UserAccount> findAllByRolesId(@NonNull Long roles);
    boolean existsByUsername(@NonNull String name);
}