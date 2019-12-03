package com.lindaring.base.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.properties.SecurityProperties;
import com.lindaring.base.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private CustomUserDetailsService userDetailsService;
  private SecurityProperties securityProperties;

  public JWTAuthenticationFilter(CustomUserDetailsService userDetailsService, SecurityProperties securityProperties) {
    this.userDetailsService = userDetailsService;
    this.securityProperties = securityProperties;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    try {
      RegisteredUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), RegisteredUser.class);
      return userDetailsService.authenticateUser(applicationUser);
    } catch (IOException e) {
      throw new BadCredentialsException("Invalid username and(or) password.", e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
          throws IOException {
    String token = userDetailsService.getToken(authResult);
    response.getWriter().write(token);
    response.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);
  }
}
