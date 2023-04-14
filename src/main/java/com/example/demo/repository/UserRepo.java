package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.userBankLogger " +
            "LEFT JOIN FETCH u.bankAccount " +
            "LEFT JOIN FETCH u.userInformation " +
            "WHERE u.userBankLogger.login = ?1")
    List<User> findAllWithDetailsByLogin(String login);

}
