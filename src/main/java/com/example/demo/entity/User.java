package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "name",nullable = false)
    private String imie;
    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_logger")
    private UserBankLogger userBankLogger;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account")
    private UserBankAccount bankAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private UserInformation userInformation;

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(UserBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public UserBankLogger getUserBankLogger() {
        return userBankLogger;
    }

    public void setUserBankLogger(UserBankLogger userBankLogger) {
        this.userBankLogger = userBankLogger;
    }

    public User(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public User() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                '}';
    }
}
