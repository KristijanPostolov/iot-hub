package com.iothub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iothub.model.dto.LogInRequest;
import com.iothub.model.dto.LoginResponse;
import com.iothub.model.dto.SignUpRequest;
import com.iothub.service.AccountService;
import com.iothub.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {

  private final AuthenticationService authenticationService;
  private final AccountService accountService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LogInRequest logInRequest) {
    String jwt = authenticationService.authenticate(logInRequest);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<LoginResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
    boolean createdAccount = accountService.signUpUser(signUpRequest);
    if (!createdAccount) {
      return ResponseEntity.badRequest().build();
    }

    LogInRequest logInRequest = new LogInRequest(signUpRequest.getEmail(), signUpRequest.getPassword());
    String jwt = authenticationService.authenticate(logInRequest);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }

  @GetMapping("/logout")
  public void logout(@RequestHeader(name = "Authentication") String authenticationHeader) {
    authenticationService.logout(authenticationHeader);
  }

}
