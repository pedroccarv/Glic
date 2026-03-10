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

    public Double carbsQuantity() {
        if (appliedUnits != null && user.getCarbCounting() != null && insulin != null) {
            if (List.of(InsulinType.Rapid, InsulinType.Short).contains(insulin.getType())){
                return (double) user.getCarbCounting() * appliedUnits;
            }
        }
    return 0.0;
    }
}
