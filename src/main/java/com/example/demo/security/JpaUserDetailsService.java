package com.example.demo.security;

import com.example.demo.entity.UserBankLogger;
import com.example.demo.repository.UserBankLoggerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private  UserBankLoggerRepo userBankLoggerRepo;
    @Autowired
    public JpaUserDetailsService(UserBankLoggerRepo userBankLoggerRepo) {
        this.userBankLoggerRepo = userBankLoggerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userBankLoggerRepo.findByLogin(username)
                .map(UserSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("NOt FOUND"));
    }

}
