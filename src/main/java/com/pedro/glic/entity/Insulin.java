package com.pedro.glic.entity;

import com.pedro.glic.dto.InsulinRequestDTO;
import com.pedro.glic.enums.InsulinFormat;
import com.pedro.glic.enums.InsulinType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Insulin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer units;

    private BigDecimal price;

    private Integer quantity;

    private LocalDate openingDate;

    private LocalDate purchaseDate;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private InsulinType type;

    @Enumerated(EnumType.STRING)
    private InsulinFormat format;

    public Insulin() {}

    public Insulin(String name, String description, Integer units, BigDecimal price, Integer quantity, LocalDate openingDate, LocalDate purchaseDate, InsulinType type, InsulinFormat format) {
        this.name = name;
        this.description = description;
        this.units = units;
        this.price = price;
        this.quantity = quantity;
        this.openingDate = openingDate;
        this.purchaseDate = purchaseDate;
        this.type = type;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InsulinFormat getFormat() {
        return format;
    }

    public void setFormat(InsulinFormat format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public InsulinType getType() {
        return type;
    }

    public void setType(InsulinType type) {
        this.type = type;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void updateFrom(InsulinRequestDTO dto) {
        if (dto.name() != null) this.name = dto.name();
        if (dto.description() != null) this.description = dto.description();
        if (dto.units() != null) this.units = dto.units();
        if (dto.purchaseDate() != null) this.purchaseDate = dto.purchaseDate();
        if (dto.openingDate() != null) this.openingDate = dto.openingDate();
        if (dto.quantity() != null) this.quantity = dto.quantity();
        if (dto.type() != null) this.type = dto.type();
        if (dto.format() != null) this.format = dto.format();
    }

}
