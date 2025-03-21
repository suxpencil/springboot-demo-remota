package com.b2b.spring.boot.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private final AuthorizationHeaderFilter authorizationHeaderFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authorizationHeaderFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
            authReq -> authReq
                      .requestMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
                 //   .requestMatchers("/api/auth/user").hasRole("USER")
                 //   .requestMatchers("/api/auth/admin").hasRole("ADMIN")
                    .requestMatchers("/user/**").permitAll() //  "**" dice che tutti i path con /USER/ e dopo qualsiasi altra cosa avranno il permitAll()
                .anyRequest().authenticated());
    return httpSecurity.build();
  }

}
