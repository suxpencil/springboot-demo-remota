package com.b2b.spring.boot.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private static final long JWT_TOKEN_MILLIS_VALIDITY = 60 * 60 * 1000L;
  private static final long JWT_REFRESH_TOKEN_MILLIS_VALIDITY = 24 * 60 * 60 * 1000L;
  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_ISSUED_AT = "iat";
  private static final String CLAIM_KEY_EXPIRATION = "exp";
  private static final String CLAIM_KEY_AUTHORITIES = "roles";

  private final UserDetailsService userDetailsService;

  @Value("${jwt.secretKey}")
  private String secretKey;

  public JwtUtils(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * Extracts and returns the username from the provided JWT token.
   *
   * @param token the JWT token from which the username will be extracted
   * @return the username extracted from the token
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Extracts and returns the expiration date from the provided JWT token.
   *
   * @param token the JWT token from which the expiration date will be extracted
   * @return the expiration date extracted from the token
   */
  public Date getExpirationFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Generates a JWT token for the provided user details.
   *
   * @param userDetails the user details used to generate the JWT token
   * @return the generated JWT token as a string
   */
  public String generateToken(UserDetails userDetails) {
    return this.generateJwtToken(userDetails, JWT_TOKEN_MILLIS_VALIDITY);
  }

  /**
   * Generates a refresh JWT token for the provided user details with a specified validity period.
   *
   * @param userDetails the user details used to generate the refresh JWT token
   * @return the generated refresh JWT token as a string
   */
  public String generateRefreshToken(UserDetails userDetails) {
    return this.generateJwtToken(userDetails, JWT_REFRESH_TOKEN_MILLIS_VALIDITY);
  }

  /**
   * Validates the provided JWT token by checking if the username extracted from the token matches
   * the username in the token's claims and ensures the token is not expired.
   *
   * @param token the JWT token to be validated
   * @return true if the token is valid, false otherwise
   */
  public Boolean validateToken(String token) {
    return getUsernameFromToken(token).equals(getAllClaimsFromToken(token).getSubject())
        && !isTokenExpired(token);
  }

  /**
   * Checks whether the provided JWT token has expired.
   *
   * @param token the JWT token to check for expiration
   * @return true if the token has expired, false otherwise
   */
  public Boolean isTokenExpired(String token) {
    return getClaimFromToken(token, Claims::getExpiration).before(new Date());
  }

  /**
   * Retrieves the user information encapsulated within the provided JWT token.
   *
   * @param token the JWT token containing the user information
   * @return the UserDetails object representing the user associated with the token
   * @throws RuntimeException if no claims can be retrieved from the token
   */
  public UserDetails getUserFromToken(String token) {
    Claims claims = getAllClaimsFromToken(token);
    if (claims != null) {
      return this.userDetailsService.loadUserByUsername(claims.getSubject());
    } else {
      throw new RuntimeException("No claims for parsed token");
    }
  }

  /**
   * Retrieves a claim from the specified JWT token by using the provided claim resolver function.
   *
   * @param <T>            the type of the claim to be extracted
   * @param token          the JWT token from which the claim will be extracted
   * @param claimsResolver a function used to extract a specific claim from the token's claims
   * @return the extracted claim of type T
   */
  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts and returns all claims from the provided JWT token.
   *
   * @param token the JWT token from which all claims will be extracted
   * @return the extracted claims from the token
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Generates a JWT token using the provided user details and validity period.
   *
   * @param userDetails the user details used to populate the claims of the JWT token
   * @param validity    the duration in milliseconds for which the token will remain valid
   * @return the generated JWT token as a string
   */
  private String generateJwtToken(UserDetails userDetails, Long validity) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_ISSUED_AT, new Date(System.currentTimeMillis()));
    claims.put(CLAIM_KEY_EXPIRATION, new Date(System.currentTimeMillis() + validity));
    List<String> auth = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .toList();
    claims.put(CLAIM_KEY_AUTHORITIES, auth);
    return Jwts.builder().claims(claims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + validity))
        .signWith(getSigningKey(), SIG.HS256).compact();
  }

  /**
   * Generates and returns the signing key to be used for JWT token creation and verification.
   *
   * @return the signing key as a SecretKey object
   */
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
  }

}
