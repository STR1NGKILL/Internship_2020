package org.reallume.service;

import org.reallume.domain.ActionOfRights;
import org.reallume.domain.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String login;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl() { }

    public UserDetailsImpl(Employee user) {
        this.login = user.getLogin();
        this.password = user.getPassword();

        String authorities = "";
        for (ActionOfRights actionOfRights : user.getRights().getActionOfRights()) {
            authorities += actionOfRights.getAction().getName() + ",";
        }

        authorities = authorities.substring(0, authorities.length() - 1);

        this.authorities = Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public void setUsername(String username) {
        this.login = username;
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
        return true;
    }
}

