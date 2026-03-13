package com.pedro.glic.entity;

import com.pedro.glic.dto.ApplicationLogRequestDTO;
import com.pedro.glic.dto.ApplicationLogResponseDTO;
import com.pedro.glic.dto.InsulinRequestDTO;
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

    public ApplicationLog() {}

    public ApplicationLog(Integer appliedUnits, LocalDateTime applicationTime, Double consumedCarbs,User user, Insulin insulin) {
        this.appliedUnits = appliedUnits;
        this.applicationTime = applicationTime;
        this.consumedCarbs = consumedCarbs;
        this.user = user;
        this.insulin = insulin;
    }

    public Long getId() {
        return id;
    }

    public Integer getAppliedUnits() {
        return appliedUnits;
    }

    public Double getConsumedCarbs() {
        return consumedCarbs;
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

    public void updateFrom(ApplicationLogRequestDTO dto) {
        if (dto.appliedUnits() != null) this.appliedUnits = dto.appliedUnits();
        if (dto.consumedCarbs() != null) this.consumedCarbs = dto.consumedCarbs();
        if (dto.applicationTime() != null) this.applicationTime = dto.applicationTime();
    }
}
