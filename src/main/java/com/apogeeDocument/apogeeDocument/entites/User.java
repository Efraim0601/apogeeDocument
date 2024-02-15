package com.apogeeDocument.apogeeDocument.entites;

import com.apogeeDocument.apogeeDocument.enumerable.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
@Entity
public class User implements UserDetails {

    @Id
    private Integer numero_employe=0;
    private String name;
    private String surname;
    private String email;
    private Integer numero;
    @Column(name = "password")
    private String password;
    private boolean isActive = false;

    @OneToOne(cascade=CascadeType.ALL)
    private Role wording;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE" + this.wording.getWording()));
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}

