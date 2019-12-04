package com.lindaring.base.security.config;

import com.lindaring.base.properties.SecurityProperties;
import com.lindaring.base.security.filter.JWTAuthenticationFilter;
import com.lindaring.base.security.filter.JWTAuthorizationFilter;
import com.lindaring.base.security.service.CustomUserDetailsService;
import com.lindaring.base.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permittedUrls = {
                "/login",
                "/base-six-four/v1/base64/**",
                "/base-six-four/v1/cache/**",
                "/base-six-four/v1/cronjob/**",
                "/base-six-four/v1/year/**",
                "/base-six-four/v1/url/**",
                "/base-six-four/v1/user/**"
        };

        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(permittedUrls).permitAll()
            .antMatchers("/base-six-four/secure/v1/**").hasAnyRole("USER", "ADMIN")
            .and()
            .addFilter(new JWTAuthenticationFilter(customUserDetailsService, securityProperties))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService, securityProperties));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return GeneralUtils.getPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
