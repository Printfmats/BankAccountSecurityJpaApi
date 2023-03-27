package com.example.demo.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
@Table(name = "UserBank")
public class UserBank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_bank")
    private Long idBank;
    @Column(name = "bank_name", nullable = false)
    private String bankName;
    @Column(name = "can_get_credit")
    private Boolean canGetCredit;

    public UserBank() {
    }

    public UserBank(String bankName, Boolean canGetCredit) {
        this.bankName = bankName;
        this.canGetCredit = canGetCredit;
    }

    @Override
    public String toString() {
        return "UserBank{" +
                "idBank=" + idBank +
                ", bankName='" + bankName + '\'' +
                ", canGetCredit=" + canGetCredit +
                '}';
    }

    public Long getIdBank() {
        return idBank;
    }

    public void setIdBank(Long idBank) {
        this.idBank = idBank;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Boolean getCanGetCredit() {
        return canGetCredit;
    }

    public void setCanGetCredit(Boolean canGetCredit) {
        this.canGetCredit = canGetCredit;
    }
}
