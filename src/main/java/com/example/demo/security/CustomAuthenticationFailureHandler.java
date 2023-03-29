package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final int MAX_ATTEMPTS = 3;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        HttpSession session = request.getSession();

        Integer attempts = (Integer) session.getAttribute("attempts");

        if (attempts == null) {
            attempts = 1;
        } else {
            attempts++;
        }

        session.setAttribute("attempts", attempts);

        if (attempts >= MAX_ATTEMPTS) {
            response.sendRedirect("/lockout");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}

