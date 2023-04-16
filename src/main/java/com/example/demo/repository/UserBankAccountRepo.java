package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankAccountRepo extends JpaRepository<UserBankAccount,Long> {
    Optional<UserBankAccount> findByIdAccount(Long idAccount);

    @Query("SELECT saldo FROM UserBankAccount WHERE idAccount = :idAccount")
    Double findSaldoByIdAccount(@Param("idAccount") Long idAccount);
}
