package com.lindaring.base.service;

import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.properties.SecurityProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private final CustomUserDetailsService customUserDetailsService;
  private SecurityProperties securityProperties;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                CustomUserDetailsService customUserDetailsService,
                                SecurityProperties securityProperties) {
    super(authenticationManager);
    this.customUserDetailsService = customUserDetailsService;
    this.securityProperties = securityProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String header = getHeadersInfo(request, "authorization");
    if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
      chain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthenticationToken(request);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    chain.doFilter(request, response);
  }

  private String getHeadersInfo(HttpServletRequest request, String searchKey) {
    Enumeration headerNames = request.getHeaderNames();

    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      if (key.equalsIgnoreCase(searchKey)) {
        return request.getHeader(key);
      }
    }

    return null;
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
    String token = getHeadersInfo(request, "authorization");
    if (token == null) return null;
    String username = Jwts.parser().setSigningKey(securityProperties.getSecret())
            .parseClaimsJws(token.replace(securityProperties.getTokenPrefix(), ""))
            .getBody()
            .getSubject();
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
    RegisteredUser appUser = customUserDetailsService.getUser(username);
    return username != null ? new UsernamePasswordAuthenticationToken(appUser, null, userDetails.getAuthorities()) : null;
  }
}
