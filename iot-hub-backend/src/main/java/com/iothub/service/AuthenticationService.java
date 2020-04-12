package com.iothub.service;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iothub.model.JwtBlacklist;
import com.iothub.model.dto.LogInRequest;
import com.iothub.repository.JwtBlacklistRepository;
import com.iothub.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final AccountService accountService;
  private final JwtUtil jwtUtil;
  private final JwtBlacklistRepository jwtBlacklistRepository;

  /**
   * Authenticates with username and password credentials.
   * @param logInRequest {@link LogInRequest}.
   * @return jwt token.
   */
  public String authenticate(LogInRequest logInRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(logInRequest.getEmail(), logInRequest.getPassword()));
    UserDetails userDetails = accountService.loadUserByUsername(logInRequest.getEmail());
    return jwtUtil.generateToken(userDetails);
  }

  public void logout(String authenticationHeader) {
    String jwt = authenticationHeader.substring(7);
    String jti = jwtUtil.extractJti(jwt);
    Date date = jwtUtil.extractExpiration(jwt);
    JwtBlacklist jwtBlacklist = new JwtBlacklist(jti, date.toInstant());
    jwtBlacklistRepository.save(jwtBlacklist);
  }

}
