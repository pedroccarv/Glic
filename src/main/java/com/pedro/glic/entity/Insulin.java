package com.pedro.glic.entity;

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

    @Enumerated(EnumType.STRING)
    private InsulinType type;

    @Enumerated(EnumType.STRING)
    private InsulinFormat format;

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
}
