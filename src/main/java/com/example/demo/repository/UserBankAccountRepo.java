package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankAccountRepo extends JpaRepository<UserBankAccount,Long> {
}
