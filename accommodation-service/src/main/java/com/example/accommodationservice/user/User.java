package com.example.accommodationservice.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstname;
    @NotBlank(message = "Last name is required")
    private String lastname;
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private List<UserRoles> roles;

    private Boolean enabled;

    private Boolean accountNotExpired;
    private Boolean accountNotLocked;
    private Boolean credentialsNonExpired;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @PrePersist
    protected void onCreate(){
        this.setEnabled(true);
        this.setAccountNotExpired(true);
        this.setAccountNotLocked(true);
        this.setCredentialsNonExpired(true);

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){

    }
}
