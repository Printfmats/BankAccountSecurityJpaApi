package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UserBankAccount")
public class UserBankAccount {
    @Id
    @Column(name = "id_account")
    private Long idAccount;
    @Column(name = "saldo",nullable = false)
    private double saldo;

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

    public UserBankAccount(Long idAccount, double saldo) {
        this.idAccount = idAccount;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "UserBankAccount{" +
                "idAccount=" + idAccount +
                ", saldo=" + saldo ;
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

}
