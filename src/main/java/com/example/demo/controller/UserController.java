package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class UserController {

    @RequestMapping("/lockout")
    public String lockoutPage() {
        return "lockout";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginpage";
    }

    @RequestMapping("/registration")
    public String registerPage(){
        return "registerpage";
    }

    @RequestMapping("/api/profil")
    public String apiProfil() {
        return "userinfopage";
    }

    @RequestMapping("/api/payment")
    public String apiPayment() {
        return "paymentpage";
    }

    @RequestMapping("/api/paycheck")
    public String apiPaycheck() {
        return "paycheckpage";
    }
    @RequestMapping("/api/transfer")
    public String apiTransfer() {
        return "transferpage";
    }

    @RequestMapping("/api/mytransactions")
    public String apiMyTransactions() {
        return "mytransactionspage";
    }
}
