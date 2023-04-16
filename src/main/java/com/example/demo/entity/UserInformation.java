package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "UserInformation")
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_adress")
    private Long idAdress;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city", nullable = false)
    private  String city;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "phone", nullable = false)
//    @Pattern(regexp="(^$|[0-9]{9})")
    private String phone;

    @OneToOne(mappedBy = "userInformation")
    private UserBankLogger userBankLogger;

    public UserBankLogger getUserBankLogger() {
        return userBankLogger;
    }

    public void setUserBankLogger(UserBankLogger userBankLogger) {
        this.userBankLogger = userBankLogger;
    }

    public UserInformation() {
    }

    public UserInformation(String country, String city, String street, String phone) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "UserInformation{" +
                "idAdress=" + idAdress +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", strett='" + street + '\'' +
                ", phone=" + phone +
                '}';
    }

    public Long getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(Long idAdress) {
        this.idAdress = idAdress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStrett() {
        return street;
    }

    public void setStrett(String strett) {
        this.street = strett;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
