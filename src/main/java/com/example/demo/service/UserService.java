package com.example.demo.service;

import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


}
