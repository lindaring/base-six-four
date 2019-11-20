package com.lindaring.base.security.service;

import com.lindaring.base.dto.RegisteredUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        RegisteredUser appUser = getUser(username);
        return new User(appUser.getUsername(), appUser.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    public RegisteredUser getUser(String username) {
        return new RegisteredUser("batman", "$2y$05$o/y38i5JnMYLW3gi4ImIiuGIgo85onzEQaqWu9BpnNsriEoxaV8J6", null);
    }
}
