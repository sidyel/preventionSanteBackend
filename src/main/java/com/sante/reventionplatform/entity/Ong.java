package com.sante.reventionplatform.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "ongs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String nomOng;

    @Column
    private String numeroEnregistrement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomaineIntervention domaineIntervention;

    @Column(columnDefinition = "TEXT")
    private String mission;

    @Column(columnDefinition = "TEXT")
    private String vision;

    @Column
    private String siteWeb;

    @Column
    private Integer nombreEmployes;

    @Column
    private String zonesIntervention;
}