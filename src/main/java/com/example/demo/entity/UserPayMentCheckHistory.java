package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserPayMentCheckHistory")
public class UserPayMentCheckHistory {
    @Id
    @Column(name = "id_paycheck_payment")
    private Long idPaycheckPayment;
    @Column(name = "nr_account", nullable = false)
    private Long nrAccount;
    @Column(name = "transfer_type", nullable = false)
    private String transactionType;
    @Column(name = "amount", nullable = false)
    private String amount;
    @Column(name = "description")
    private String description;

    public UserPayMentCheckHistory(Long nrAccount, String transactionType, String amount, String description) {
        this.nrAccount = nrAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }

    public UserPayMentCheckHistory() {

    }

    @Override
    public String toString() {
        return "UserPayMentCheckHistory{" +
                "idPaycheckPayment=" + idPaycheckPayment +
                ", nrAccount=" + nrAccount +
                ", transactionType='" + transactionType + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getIdPaycheckPayment() {
        return idPaycheckPayment;
    }

    public void setIdPaycheckPayment(Long idPaycheckPayment) {
        this.idPaycheckPayment = idPaycheckPayment;
    }

    public Long getNrAccount() {
        return nrAccount;
    }

    public void setNrAccount(Long nrAccount) {
        this.nrAccount = nrAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

