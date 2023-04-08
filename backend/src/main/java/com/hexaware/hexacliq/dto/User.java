package com.hexaware.hexacliq.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_master")
public class User implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled = true;
    private String profile;
    private Integer empId;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageData;
    @Transient
    private boolean isPasswordMatch = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public User() {

    }

    @Override
    public Collection<Authority> getAuthorities() {
        Set<Authority> set = new HashSet<>();
        this.userRoles.forEach(ur -> {
            ur.getRole().getPrivileges().stream().map(Privileges::getName)
                    .map(Authority::new)
                    .forEach(set::add);
        });
        return set;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return false;
    }

}
