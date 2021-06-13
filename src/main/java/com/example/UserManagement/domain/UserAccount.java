package com.example.UserManagement.domain;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue
    Long Id;

    @Column(unique = true)
    @Length(min = 3, max = 16, message = "Username must be between 3 and 16")
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Should contain only latin characters values")
    @NonNull
    String username;


    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,16}$", message = "Password should only contain alphanumeric values, and At least one symbol and one digit")
    @Length(min = 3, max = 16, message = "Password must be between 3 and 16")
    @NotBlank(message = "Password cannot be empty")
    @NonNull
    String password;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Should contain only latin characters values")
    @Length(min = 1, max = 16, message = "First name must be between 1 and 16")
    @NonNull
    String firstName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Should contain only latin characters values")
    @Length(min = 1, max = 16, message = "Last name must be between 1 and 16")
    @NonNull
    String lastName;

    boolean active;

    @NotNull
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    Role roles;

    public UserAccount(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, boolean active, Role roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRoles());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}