package com.lindaring.base.security.service;

import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.entity.UserEntity;
import com.lindaring.base.enums.RoleType;
import com.lindaring.base.properties.SecurityProperties;
import com.lindaring.base.repo.UsersRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) {
        RegisteredUser appUser = getUser(username);
        return new User(appUser.getUsername(), appUser.getPassword(), true, true, true,
                true, getAuthorities(appUser.getRoles()));
    }

    public Authentication authenticateUser(RegisteredUser applicationUser) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));
    }

    public String getToken(Authentication authenticationResult) {
        ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(securityProperties.getExpirationTime(),
                ChronoUnit.MILLIS);
        return Jwts.builder().setSubject(((User) authenticationResult.getPrincipal()).getUsername())
                .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256, securityProperties.getSecret())
                .compact();
    }

    public RegisteredUser getUser(String username) {
        Optional<UserEntity> userOptional = usersRepo.getUserByUsername(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            boolean activated = isActivated(user);
            if (!activated){
                throw new DisabledException("User account not activated.");
            }
            return new RegisteredUser(user.getUsername(), user.getPassword(), activated, getRoleTypes(user));
        }
        throw new UsernameNotFoundException("User not found.");
    }

    private boolean isActivated(UserEntity userEntity) {
        return (userEntity.getActive() == 1);
    }

    private List<GrantedAuthority> getAuthorities(List<RoleType> roleTypes) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roleTypes.forEach(r -> roles.add((GrantedAuthority) r::getFullDescription));
        return roles;
    }

    private List<RoleType> getRoleTypes(UserEntity userEntity) {
        List<RoleType> types = new ArrayList<>();
        userEntity.getRoles().forEach(r -> RoleType.getType(r.getDesc()).ifPresent(types::add));
        return types;
    }
}
