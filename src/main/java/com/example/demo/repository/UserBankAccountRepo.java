package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankAccountRepo extends JpaRepository<UserBankAccount,Long> {
    Optional<UserBankAccount> findByIdAccount(Long idAccount);
}
