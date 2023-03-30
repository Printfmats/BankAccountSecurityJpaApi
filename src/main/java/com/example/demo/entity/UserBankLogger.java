package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "UserBankLogger ")
public class UserBankLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUserLogger;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private  String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "rola", nullable = false)
    private Roles role;

    @Column(name = "blocked")
    private Boolean blocked;

    @OneToOne(mappedBy = "userBankLogger", cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBankLogger(String login, String password, Roles role) {
        this.login = login;
        this.password = password;
        this.role = role;
        blocked = false;
    }

    public UserBankLogger() {
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Long getIdUserLogger() {
        return idUserLogger;
    }

    public void setIdUserLogger(Long idUserLogger) {
        this.idUserLogger = idUserLogger;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserBankLogger{" +
                "idUserLogger=" + idUserLogger +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
