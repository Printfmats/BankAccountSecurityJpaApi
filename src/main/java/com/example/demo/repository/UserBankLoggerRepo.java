package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBankLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBankLoggerRepo extends JpaRepository<UserBankLogger,Long> {
    Optional<UserBankLogger>  findByLogin(String username);

    @Query("SELECT bankAccount.idAccount FROM UserBankLogger logger JOIN logger.bankAccount bankAccount WHERE logger.login = :login")
    Long findIdAccountByLogin(@Param("login") String login);

    @Query("SELECT u FROM UserBankLogger u " +
            "LEFT JOIN FETCH u.user " +
            "LEFT JOIN FETCH u.bankAccount " +
            "LEFT JOIN FETCH u.userInformation " +
            "WHERE u.login = :login")
    List<UserBankLogger> findAllWithDetailsByLogin(@Param("login") String login);


}
