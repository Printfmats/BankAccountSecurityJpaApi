package com.example.demo.security;

import com.example.demo.entity.UserBankLogger;
import com.example.demo.repository.UserBankLoggerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserBankLoggerRepo userBankLoggerRepo;
    @Autowired
    public JpaUserDetailsService(UserBankLoggerRepo userBankLoggerRepo) {
        this.userBankLoggerRepo = userBankLoggerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserBankLogger> optionalUserAccount = userBankLoggerRepo.findByUsername(username);
        if (optionalUserAccount.isPresent()) {
            UserBankLogger userBankLogger = optionalUserAccount.get();
            return new UserSecurity(userBankLogger);
        } else {
            throw new UsernameNotFoundException("Not Found");
        }
    }

}
