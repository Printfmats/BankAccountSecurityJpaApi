package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class UserController {

    @RequestMapping("/lockout")
    public String lockoutPage() {
        return "lockout";
    }
    @RequestMapping("/api")
    public String apiPage() {
        return "apipage";
    }
    @RequestMapping("/login")
    public String logingPage(){
        return "loginpage";
    }

    @RequestMapping("/registration")
    public String registerPage(){
        return "register";
    }
}
