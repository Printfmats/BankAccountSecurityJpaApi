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
    @Column(name = "nazwisko", nullable = false)  //Here should be surname instead of nazwisko
    private String nazwisko;

    @OneToOne(mappedBy = "user")
    private UserBankLogger userBankLogger;

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
