package com.pedro.glic.entity;

import com.pedro.glic.enums.DiabetesType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private LocalDate birthday;

    private LocalDate diagnostic;

    private String phone;

    private Integer carbCounting;

    @Enumerated(EnumType.STRING)
    private DiabetesType diabetesType;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Insulin> insulins;

    public Long getId() {
        return id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getCarbCounting() {
        return carbCounting;
    }

    public void setCarbCounting(Integer carbCounting) {
        this.carbCounting = carbCounting;
    }

    public DiabetesType getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(DiabetesType diabetesType) {
        this.diabetesType = diabetesType;
    }

    public LocalDate getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(LocalDate diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
