package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class UserController {

    private final UserRepo userRepository;
    private final UserInformationRepo userInformationRepository;
    private final UserBankLoggerRepo userBankLoggerRepo;
    private final UserBankAccountRepo userBankAccountRepo;
    private final UserPayMentCheckHistoryRepo userPayMentCheckHistoryRepo;
    private final UserTransferHistoryRepo userTransferHistoryRepo;

    @Autowired
    public UserController(UserRepo userRepository, UserInformationRepo userInformationRepository, UserBankLoggerRepo userBankLoggerRepo, UserBankAccountRepo userBankAccountRepo, UserPayMentCheckHistoryRepo userPayMentCheckHistoryRepo, UserTransferHistoryRepo userTransferHistoryRepo) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.userBankLoggerRepo = userBankLoggerRepo;
        this.userBankAccountRepo = userBankAccountRepo;
        this.userPayMentCheckHistoryRepo = userPayMentCheckHistoryRepo;
        this.userTransferHistoryRepo = userTransferHistoryRepo;
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

    @GetMapping("/api/payment")
    public String apiPayment() {
        return "paymentpage";
    }
    @PostMapping("/api/payment")
    public String apiPayment(Model model, @AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("amount") String amount,
                             @RequestParam("password") String password,
                             @RequestParam("description") String description){

        String username = userDetails.getUsername();
        Long idaccount = userBankLoggerRepo.findIdAccountByLogin(username);
        Optional<UserBankAccount> userBankAccountOpt = userBankAccountRepo.findByIdAccount(idaccount);

        if (userDetails.getPassword().equals(password)) {
            if (userBankAccountOpt.isPresent()) {
                UserBankAccount userBankAccount = userBankAccountOpt.get();

                // Aktualizacja salda
                double currentSaldo = userBankAccount.getSaldo();
                double takenAmount = Double.parseDouble(amount);
                if(currentSaldo>=takenAmount){
                    double updatedSaldo = currentSaldo - takenAmount;
                    userBankAccount.setSaldo(updatedSaldo);

                    // Zapisanie zmienionej encji w bazie danych
                    userBankAccountRepo.save(userBankAccount);

                    UserPayMentCheckHistory user = new UserPayMentCheckHistory();
                    user.setNrAccount(idaccount);
                    user.setTransactionType("payment");
                    user.setAmount(amount);
                    user.setDescription(description);

                    userPayMentCheckHistoryRepo.save(user);
                }

            }
            return "redirect:/api/payment";
        } else {
            model.addAttribute("error", "Błędne hasło"); // Dodanie komunikatu błędu do modelu
            return "redirect:/api/payment"; // Przekierowanie na stronę z komunikatem błędu
        }
    }

    @GetMapping("/api/paycheck")
    public String apiPaycheck(){
        return "paycheckpage";
    }
    @PostMapping("/api/paycheck")
    public String apiPaycheck(Model model, @AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam("amount") String amount,
                              @RequestParam("password") String password,
                              @RequestParam("description") String description) {

        String username = userDetails.getUsername();
        Long idaccount = userBankLoggerRepo.findIdAccountByLogin(username);
        Optional<UserBankAccount> userBankAccountOpt = userBankAccountRepo.findByIdAccount(idaccount);

        if (userDetails.getPassword().equals(password)) {
            if (userBankAccountOpt.isPresent()) {
                UserBankAccount userBankAccount = userBankAccountOpt.get();

                // Aktualizacja salda
                double currentSaldo = userBankAccount.getSaldo();
                double addedAmount = Double.parseDouble(amount);
                double updatedSaldo = currentSaldo + addedAmount;
                userBankAccount.setSaldo(updatedSaldo);

                // Zapisanie zmienionej encji w bazie danych
                userBankAccountRepo.save(userBankAccount);

                UserPayMentCheckHistory user = new UserPayMentCheckHistory();
                user.setNrAccount(idaccount);
                user.setTransactionType("paycheck");
                user.setAmount(amount);
                user.setDescription(description);

                userPayMentCheckHistoryRepo.save(user);
            }
            return "redirect:/api/paycheck";
        } else {
            model.addAttribute("error", "Błędne hasło"); // Dodanie komunikatu błędu do modelu
            return "redirect:/api/paycheck"; // Przekierowanie na stronę z komunikatem błędu
        }
    }


    @GetMapping("/api/transfer")
    public String apiTransfer() {
        return "transferpage";
    }

    @PostMapping("/api/transfer")
    public String apiTransfer(Model model, @AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam("account") Long account,
                              @RequestParam("amount") String amount,
                              @RequestParam("password") String password,
                              @RequestParam("description") String description) {
        // Zalogowany użytkownik
        String username = userDetails.getUsername();
        Long senderId = userBankLoggerRepo.findIdAccountByLogin(username);
        Optional<UserBankAccount> userBankAccountOpt = userBankAccountRepo.findByIdAccount(senderId);
        UserBankAccount userBankAccountSender = userBankAccountOpt.get();

        if (Double.parseDouble(amount) > 0) { // Sprawdź, czy wartość amount jest >0
            if (userDetails.getPassword().equals(password)) { // Sprawdź hasło
                if (userBankAccountRepo.findById(account).isPresent()) { // Sprawdź, czy konto, do którego wysyłasz, istnieje w bazie
                    Optional<UserBankAccount> receiverIdAccount = userBankAccountRepo.findById(account);
                    UserBankAccount userBankAccountReceiver = receiverIdAccount.get();
                    // Sprawdź, czy saldo konta wysyłającego jest większe od kwoty transferu
                    if (userBankAccountSender.getSaldo() >= Double.parseDouble(amount)) {
                        // Aktualizuj saldo konta wysyłającego
                        userBankAccountSender.setSaldo(userBankAccountSender.getSaldo() - Double.parseDouble(amount));
                        // Aktualizuj saldo konta odbiorcy
                        userBankAccountReceiver.setSaldo(userBankAccountReceiver.getSaldo() + Double.parseDouble(amount));

                        // Wprowadź do bazy historii transferu między kontami
                        UserTansferHistory userTansferHistory = new UserTansferHistory();
                        userTansferHistory.setAmount(amount);
                        userTansferHistory.setDescription(description);
                        userTansferHistory.setReceiver(account);
                        userTansferHistory.setSender(senderId);

                        // Zapisz zmiany w bazie danych
                        userBankAccountRepo.save(userBankAccountSender);
                        userBankAccountRepo.save(userBankAccountReceiver);
                        userTransferHistoryRepo.save(userTansferHistory);
                    } else {
                        System.out.println("Niewystarczające saldo na koncie wysyłającego");
                    }
                } else {
                    System.out.println("Numer konta odbiorcy jest nieprawidłowy");
                }
            } else {
                System.out.println("Nieprawidłowe hasło");
            }
        } else {
            System.out.println("Kwota transferu musi być większa od 0");
        }

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
