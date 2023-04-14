package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserBankAccount;
import com.example.demo.entity.UserBankLogger;
import com.example.demo.entity.UserInformation;
import com.example.demo.repository.UserBankAccountRepo;
import com.example.demo.repository.UserBankLoggerRepo;
import com.example.demo.repository.UserInformationRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {

    private final UserRepo userRepository;
    private final UserInformationRepo userInformationRepository;
    private final UserBankLoggerRepo userBankLoggerRepo;
    private final UserBankAccountRepo userBankAccountRepo;

    @Autowired
    public UserController(UserRepo userRepository, UserInformationRepo userInformationRepository, UserBankLoggerRepo userBankLoggerRepo, UserBankAccountRepo userBankAccountRepo) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.userBankLoggerRepo = userBankLoggerRepo;
        this.userBankAccountRepo = userBankAccountRepo;
    }

    @RequestMapping("/lockout")
    public String lockoutPage() {
        return "lockout";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginpage";
    }

    @GetMapping("/registration")
    public String registerPage(){
        return "registerpage";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam Map<String, String> params) {
        // Tworzenie obiektów encji UserInformation i User
        UserInformation userInformation = new UserInformation();
        userInformation.setCountry(params.get("country"));
        userInformation.setCity(params.get("city"));
        userInformation.setStrett(params.get("street"));
        userInformation.setPhone(params.get("phone"));
        userInformationRepository.save(userInformation);

        User user = new User();
        user.setImie(params.get("name"));
        user.setNazwisko(params.get("surname"));
        user.setUserInformation(userInformation);
        userRepository.save(user);

        UserBankAccount userBankAccount = new UserBankAccount();
        Random random = new Random();
        long idAccount = Math.abs(random.nextLong() % 1000000000L); // generowanie losowej liczby o 9 cyfrach
        userBankAccount.setIdAccount(idAccount);
        userBankAccount.setSaldo(0);
        userBankAccountRepo.save(userBankAccount);

        UserBankLogger userBankLogger = new UserBankLogger();
        userBankLogger.setLogin(params.get("username"));
        userBankLogger.setPassword(params.get("password"));
        userBankLogger.setRole(params.get("role"));
        userBankLoggerRepo.save(userBankLogger);

        user.setBankAccount(userBankAccount);
        user.setUserBankLogger(userBankLogger);
        userRepository.save(user);
        return "redirect:/login";
    }



    @GetMapping("/api/profil")  //Ta funkcjonalności posiada wadę wymaga zmiany wyszukiwania za pomocą loginu na id, ponieważ może być wiele takich samych loginów
    public String apiProfil(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername(); // Pobieranie loginu z UserDetails
        List<User> users = userRepository.findAllWithDetailsByLogin(login);
        model.addAttribute("user", users.get(0));
        return "userinfopage";
    }



    @RequestMapping("/api/payment")
    public String apiPayment() {
        return "paymentpage";
    }

    @GetMapping("/api/paycheck")
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
