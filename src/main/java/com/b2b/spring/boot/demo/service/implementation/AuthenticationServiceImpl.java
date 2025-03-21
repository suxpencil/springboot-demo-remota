package com.b2b.spring.boot.demo.service.implementation;

import com.b2b.spring.boot.demo.service.AuthenticationService;
import com.b2b.spring.boot.demo.utils.JwtUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtils jwtUtils;

  @Value("${jwt.pippo}")
  private String pippo;

  @PostConstruct
  void stampaPippo(){
    System.out.println(this.pippo);
  }

  public AuthenticationServiceImpl(@Lazy AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService, JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtils = jwtUtils;
  }



  /**
   * @throws BadCredentialsException {@inheritDoc}
   * @throws LockedException         {@inheritDoc}
   * @throws DisabledException       {@inheritDoc}
   */
  @Override
  public String login(String username, String password) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        username, password);

    try {
      this.authenticationManager.authenticate(authToken);
    } catch (BadCredentialsException | LockedException | DisabledException e) {
      throw new RuntimeException(e);
    }

    return this.jwtUtils.generateToken(userDetails);
  }




}
