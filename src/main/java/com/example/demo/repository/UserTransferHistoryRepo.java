package com.example.demo.repository;

import com.example.demo.entity.UserTansferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransferHistoryRepo extends JpaRepository<UserTansferHistory,Long> {
}
