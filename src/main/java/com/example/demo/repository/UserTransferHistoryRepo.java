package com.example.demo.repository;

import com.example.demo.entity.UserTansferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransferHistoryRepo extends JpaRepository<UserTansferHistory,Long> {
    List<UserTansferHistory> findBySenderOrReceiver(@Param("sender") Long sender, @Param("receiver") Long receiver);
}
