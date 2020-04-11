package com.iothub.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iothub.model.Account;
import com.iothub.model.dto.SignUpRequest;
import com.iothub.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.findByEmail(email);
    if (account == null) {
      return null;
    }
    return new User(email, account.getPassword(), new ArrayList<>());
  }

  public Account findByEmail(String email) {
    return accountRepository.findByEmail(email);
  }

  public boolean signUpUser(SignUpRequest signUpRequest) {
    boolean accountAlreadyExists = accountRepository.existsAccountByEmail(signUpRequest.getEmail());
    if (accountAlreadyExists) {
      return false;
    }
    String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
    Account account = new Account(signUpRequest.getName(), signUpRequest.getEmail(), encodedPassword);
    accountRepository.save(account);
    return true;
  }
}
