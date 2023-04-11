package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepo extends JpaRepository<UserInformation,Long> {
}
