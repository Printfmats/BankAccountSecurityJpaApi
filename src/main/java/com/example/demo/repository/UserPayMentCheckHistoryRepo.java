package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserPayMentCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPayMentCheckHistoryRepo extends JpaRepository<UserPayMentCheckHistory,Long> {
    List<UserPayMentCheckHistory> findByNrAccount(long nrAccount);
}
