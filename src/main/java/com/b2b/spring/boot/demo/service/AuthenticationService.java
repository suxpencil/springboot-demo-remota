package com.b2b.spring.boot.demo.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

public interface AuthenticationService {

  /**
   * Authenticates a user using the provided username and password.
   *
   * @param username the username of the user attempting to log in
   * @param password the password associated with the provided username
   * @return a JWT token if the authentication is successful
   * @throws BadCredentialsException when the username or password does not match an existing user
   * @throws LockedException         when the username matches a locked account
   * @throws DisabledException       when the username matches a disabled account
   */
  String login(String username, String password);



}
