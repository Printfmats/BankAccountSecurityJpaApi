package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "UserBankAccount")
public class UserBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_account")
    private Long idAccount;
    @Column(name = "saldo",nullable = false)
    private double saldo;
    @Column(name = "currency",nullable = false)
    private String currency;

//    @Column(name = "account_non_locked")
//    private boolean accountNonLocked;
//
//    @Column(name = "failed_attempt")
//    private int failedAttempt;
//
//    @Column(name = "lock_time")
//    private Date lockTime;
    @OneToOne(mappedBy = "bankAccount")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBankAccount() {
    }

    public UserBankAccount(double saldo, String currency) {
        this.saldo = saldo;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "UserBankAccount{" +
                "idAccount=" + idAccount +
                ", saldo=" + saldo +
                ", currency='" + currency + '\'' +
                '}';
    }

//    public boolean isAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    public void setAccountNonLocked(boolean accountNonLocked) {
//        this.accountNonLocked = accountNonLocked;
//    }
//
//    public int getFailedAttempt() {
//        return failedAttempt;
//    }
//
//    public void setFailedAttempt(int failedAttempt) {
//        this.failedAttempt = failedAttempt;
//    }
//
//    public Date getLockTime() {
//        return lockTime;
//    }
//
//    public void setLockTime(Date lockTime) {
//        this.lockTime = lockTime;
//    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
