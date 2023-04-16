package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "UserBankLogger ")
public class UserBankLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUserLogger;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private  String password;
    @Column(name = "rola", nullable = false)
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account")
    private UserBankAccount bankAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private UserInformation userInformation;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(UserBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserBankLogger(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserBankLogger(String login, String password, String role, User user, UserBankAccount bankAccount, UserInformation userInformation) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.user = user;
        this.bankAccount = bankAccount;
        this.userInformation = userInformation;
    }

    public UserBankLogger() {
    }


    public Long getIdUserLogger() {
        return idUserLogger;
    }

    public void setIdUserLogger(Long idUserLogger) {
        this.idUserLogger = idUserLogger;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String  role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserBankLogger{" +
                "idUserLogger=" + idUserLogger +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
