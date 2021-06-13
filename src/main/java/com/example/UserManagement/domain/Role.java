package com.example.UserManagement.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role implements GrantedAuthority {
    @Id
    Long Id;

    @Column(unique = true)
    @NonNull
    String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
