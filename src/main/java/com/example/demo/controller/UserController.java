package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Controller
public class UserController {

    private final UserRepo userRepository;
    private final UserInformationRepo userInformationRepository;
    private final UserBankLoggerRepo userBankLoggerRepo;
    private final UserBankAccountRepo userBankAccountRepo;
    private final UserPayMentCheckHistoryRepo userPayMentCheckHistoryRepo;

    @Autowired
    public UserController(UserRepo userRepository, UserInformationRepo userInformationRepository, UserBankLoggerRepo userBankLoggerRepo, UserBankAccountRepo userBankAccountRepo, UserPayMentCheckHistoryRepo userPayMentCheckHistoryRepo) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.userBankLoggerRepo = userBankLoggerRepo;
        this.userBankAccountRepo = userBankAccountRepo;
        this.userPayMentCheckHistoryRepo = userPayMentCheckHistoryRepo;
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
            UserInformation userInformation = new UserInformation();  //Tabela z adresem usera
            userInformation.setCountry(params.get("country"));
            userInformation.setCity(params.get("city"));
            userInformation.setStrett(params.get("street"));
            userInformation.setPhone(params.get("phone"));
            userInformationRepository.save(userInformation);

            User user = new User();  //tabela z imieniem i nazwiskiem
            user.setImie(params.get("name"));
            user.setNazwisko(params.get("surname"));
            userRepository.save(user);

            UserBankAccount userBankAccount = new UserBankAccount(); //Tabela z kontem bankowym
            Random random = new Random();
            long idAccount = Math.abs(random.nextLong() % 1000000000L);
            userBankAccount.setIdAccount(idAccount);
            userBankAccount.setSaldo(0);
            userBankAccountRepo.save(userBankAccount);

            UserBankLogger userBankLogger = new UserBankLogger();  //tabela z danymi logowania
            userBankLogger.setLogin(params.get("username"));
            userBankLogger.setPassword(params.get("password"));
            userBankLogger.setRole(params.get("role"));
            userBankLoggerRepo.save(userBankLogger);

            userBankLogger.setBankAccount(userBankAccount);  //Powiązanie tabel z główną
            userBankLogger.setUserInformation(userInformation);
            userBankLogger.setUser(user);
            userBankLoggerRepo.save(userBankLogger);
            return "redirect:/login";
        }

    @GetMapping("/api/profil")  //Ta funkcjonalności posiada wadę wymaga zmiany wyszukiwania za pomocą loginu na id, ponieważ może być wiele takich samych loginów
    public String apiProfil(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername(); // Pobieranie loginu z UserDetails
        List<UserBankLogger> users = userBankLoggerRepo.findAllWithDetailsByLogin(login);
        model.addAttribute("user", users.get(0));
        return "userinfopage";
    }

    @RequestMapping("/api/payment")
    public String apiPayment() {
        return "paymentpage";
    }

    @GetMapping("/api/paycheck")
    public String apiPaycheck(){
        return "paycheckpage";
    }
    @PostMapping("/api/paycheck")
    public String apiPaycheck(Model model , UserSecurity userSecurity,
                              @RequestParam("amount") String amount,
                              @RequestParam("password") String password,
                              @RequestParam("description") String description){

        if(Objects.equals(userSecurity.getPassword(), password)){
            String username = userSecurity.getUsername();

            // Utwórz nowy obiekt UserPayMentCheckHistory i ustaw wartości
            UserPayMentCheckHistory user = new UserPayMentCheckHistory();
            user.setNrAccount(userBankLoggerRepo.findAccountIdByLogin(username));
            user.setTransactionType("paycheck");
            user.setAmount(amount);
            user.setDescription(description);
            System.out.println(user);

            userPayMentCheckHistoryRepo.save(user);
        }
        return "redirect:/api/paycheck";
    }


    @RequestMapping("/api/transfer")
    public String apiTransfer() {
        return "transferpage";
    }
    @RequestMapping("/api/payment-and-paycheck-history")
    public String apiPayHistory() {
        return "paymentpaycheckhistorypage";
    }
    @RequestMapping("/api/account-transfer-history")
    public String apiTransferHistory() {
        return "transferhistorypage";
    }
}
