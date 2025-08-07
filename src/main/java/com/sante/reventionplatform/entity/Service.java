package com.sante.reventionplatform.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String category; // Ex: Imagerie, Consultation, Urgence

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Tarif> tarifs;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    // Constructeurs
    public Service() {
    }

    public Service(String name, String description, String category, Hospital hospital) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.hospital = hospital;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Tarif> getTarifs() {
        return tarifs;
    }

    public void setTarifs(List<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}