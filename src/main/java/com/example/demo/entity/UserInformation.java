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
    @Column(name = "strett", nullable = false)
    private String strett;
    @Column(name = "phone", nullable = false)
    @Pattern(regexp="(^$|[0-9]{10})")
    private Integer phone;

    @OneToOne(mappedBy = "userInformation")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInformation() {
    }

    public UserInformation(String country, String city, String strett, Integer phone) {
        this.country = country;
        this.city = city;
        this.strett = strett;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "UserInformation{" +
                "idAdress=" + idAdress +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", strett='" + strett + '\'' +
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
        return strett;
    }

    public void setStrett(String strett) {
        this.strett = strett;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
