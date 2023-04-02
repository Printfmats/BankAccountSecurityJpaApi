package com.example.demo.repository;

import com.example.demo.entity.UserBankLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankLoggerRepo extends JpaRepository<UserBankLogger,Long> {
    UserBankLogger findByLogin(String username);

}
