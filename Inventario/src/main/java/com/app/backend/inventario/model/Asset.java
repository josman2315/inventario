package com.app.backend.inventario.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "Asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private String description;
    private String name;
    private LocalDate purchaseDate;
    private BigDecimal purchaseValue;

    public Asset(Long id, String serialNumber, String description, String name, LocalDate purchaseDate, BigDecimal purchaseValue) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.description = description;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.purchaseValue = purchaseValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        this.purchaseValue = purchaseValue;
    }
}
