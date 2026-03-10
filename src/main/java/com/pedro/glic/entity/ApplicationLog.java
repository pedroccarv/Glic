package com.pedro.glic.entity;

import com.pedro.glic.enums.InsulinType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ApplicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer appliedUnits;

    private LocalDateTime applicationTime;

    private Double consumedCarbs;

    @ManyToOne
    private User user;

    @ManyToOne
    private Insulin insulin;

    public Long getId() {
        return id;
    }

    public Integer getAppliedUnits() {
        return appliedUnits;
    }

    public void setAppliedUnits(Integer appliedUnits) {
        this.appliedUnits = appliedUnits;
    }

    public LocalDateTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalDateTime applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Insulin getInsulin() {
        return insulin;
    }

    public void setInsulin(Insulin insulin) {
        this.insulin = insulin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double calculateSuggestedDose() {
        if (consumedCarbs != null && user.getCarbCounting() != null && consumedCarbs > 0 && user.getCarbCounting() > 0) {
            return consumedCarbs / user.getCarbCounting();
        }
        return 0.0;
    }
}
