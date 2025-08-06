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
@Table(name = "structures_sante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StructureSante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String nomStructure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeStructure typeStructure;

    @Column
    private String numeroAgrement;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String adresse;

    @Column
    private Integer capaciteAccueil;

    @Column
    private String specialites;
}