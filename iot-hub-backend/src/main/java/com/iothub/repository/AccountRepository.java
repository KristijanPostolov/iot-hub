package com.iothub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iothub.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Account findByEmail(String email);
  
}
