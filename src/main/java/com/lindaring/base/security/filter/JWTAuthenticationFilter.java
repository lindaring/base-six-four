package com.lindaring.base.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.properties.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  private SecurityProperties securityProperties;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SecurityProperties securityProperties) {
    this.authenticationManager = authenticationManager;
    this.securityProperties = securityProperties;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    try {
      RegisteredUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), RegisteredUser.class);
      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));
    } catch (IOException e) {
      throw new BadCredentialsException("Invalid username and(or) password.", e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
          throws IOException {
    ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(securityProperties.getExpirationTime(), ChronoUnit.MILLIS);
    String token = Jwts.builder().setSubject(((User)authResult.getPrincipal()).getUsername())
            .setExpiration(Date.from(expirationTimeUTC.toInstant()))
            .signWith(SignatureAlgorithm.HS256, securityProperties.getSecret())
            .compact();
    response.getWriter().write(token);
    response.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);
  }
}
