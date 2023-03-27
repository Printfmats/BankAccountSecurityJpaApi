package com.example.demo.entity;

import jakarta.persistence.*;

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
