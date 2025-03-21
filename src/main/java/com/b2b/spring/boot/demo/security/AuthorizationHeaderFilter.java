package com.b2b.spring.boot.demo.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.b2b.spring.boot.demo.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthorizationHeaderFilter extends OncePerRequestFilter {

  private static final String TOKEN_PREFIX = "Bearer ";
  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader(AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX) || authHeader.equals(
        "Bearer2 TOKENDEFAULT") || (authHeader.startsWith(TOKEN_PREFIX) && authHeader.substring(7)
        .equals("null"))) {
      filterChain.doFilter(request, response);
      return;
    }

    String username;
    String jwtToken = authHeader.substring(7);

    try {
      username = this.jwtUtils.getUsernameFromToken(jwtToken);
    } catch (IllegalArgumentException | ExpiredJwtException e) {
      throw new RuntimeException(e);
    }



    if (Boolean.TRUE.equals(this.jwtUtils.validateToken(jwtToken))) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }
}
