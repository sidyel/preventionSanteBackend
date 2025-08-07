package com.sante.reventionplatform.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifs")
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String procedureName; // Scanner, Radio, Consultation

    @Column(nullable = false)
    private BigDecimal price;

    private String description;
    private Integer duration; // Durée en minutes

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    // Constructeurs
    public Tarif() {
    }

    public Tarif(String procedureName, BigDecimal price, Service service) {
        this.procedureName = procedureName;
        this.price = price;
        this.service = service;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}