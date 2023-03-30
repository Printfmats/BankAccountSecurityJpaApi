package com.example.demo.security;

import com.example.demo.entity.Roles;
import com.example.demo.entity.UserBankLogger;
import com.example.demo.repository.UserBankLoggerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final int MAX_ATTEMPTS = 3;
    private final Map<String, Integer> attemptsMap = new ConcurrentHashMap<>();


    @Autowired
    private UserBankLoggerRepo userBankLoggerRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        Integer attempts = attemptsMap.get(username);

        if (attempts == null) {
            attempts = 1;
        } else {
            attempts++;
        }

        attemptsMap.put(username, attempts);

        if (attempts >= MAX_ATTEMPTS) {
            Optional<UserBankLogger> userOptional = userBankLoggerRepository.findByLogin(username);
            if (userOptional.isPresent()) {
                UserBankLogger user = userOptional.get();
                user.setBlocked(true);
                user.setRole(Roles.valueOf("BLOCKED"));
                userBankLoggerRepository.save(user);
            }
            response.sendRedirect("/lockout");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}

