package com.example.demo.security;

import com.example.demo.entity.UserBankLogger;
import com.example.demo.repository.UserBankLoggerRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

        private static final int MAX_ATTEMPTS = 3;

        private UserBankLoggerRepo userBankLoggerRepository;
        private UserSecurity userSecurity;

        @Autowired
        public CustomAuthenticationFailureHandler(UserBankLoggerRepo userBankLoggerRepository) {
            this.userBankLoggerRepository = userBankLoggerRepository;
        }

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            String username = request.getParameter("username"); // pobierz nazwę użytkownika z żądania logowania
            if (username != null) {
                UserBankLogger userBankLogger = userBankLoggerRepository.findByLogin(username); // pobierz użytkownika z bazy danych
                if (userBankLogger != null) {
                    Integer attempts = userBankLogger.getFailedLoginAttempts(); // pobierz liczbę prób logowania dla danego użytkownika z bazy danych
                    if (attempts == null) {
                        attempts = 0;
                    }
                    attempts++;
                    userBankLogger.setFailedLoginAttempts(attempts); // zwiększ liczbę prób logowania dla danego użytkownika w bazie danych
                    userBankLoggerRepository.save(userBankLogger); // zapisz zmiany w bazie danych
                    if (attempts >= MAX_ATTEMPTS) {
                        // zablokuj konto użytkownika
                        userSecurity.isEnabled();
                        userBankLoggerRepository.save(userBankLogger);
                        System.out.println("ERROR");
                    }
                }
            }
        }
    }

