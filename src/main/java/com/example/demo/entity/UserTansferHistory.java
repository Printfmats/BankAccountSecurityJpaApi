package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UserTansferHistory")
public class UserTansferHistory {
    @Id
    @Column(name = "id_transfer")
    private Long idTransfer;
    @Column(name = "sender",nullable = false)
    private String sender;
    @Column(name = "receiver",nullable = false)
    private String receiver;
    @Column(name = "amount", nullable = false)
    private String amount;
    @Column(name = "description")
    private String description;

    public UserTansferHistory() {
    }

    public UserTansferHistory(String sender, String receiver, String amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public UserTansferHistory(String sender, String receiver, String amount, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserTansferHistory{" +
                "idTransfer=" + idTransfer +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(Long idTransfer) {
        this.idTransfer = idTransfer;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
