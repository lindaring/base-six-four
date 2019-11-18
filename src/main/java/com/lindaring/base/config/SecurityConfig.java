package com.lindaring.base.config;

import com.lindaring.base.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permittedUrls = {
                "/login",
                "/base-six-four/v1/base64/**"
        };

        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, permittedUrls).permitAll()
            .antMatchers("/*/base-six-four/v1/base64/**").hasRole("USER")
            .antMatchers("/*/floor2/**").hasRole("ADMIN");
//            .and()
//            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//            .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));
    }
}
