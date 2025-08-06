package com.sante.reventionplatform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "campagnes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campagne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCampagne typeCampagne;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCampagne status = StatusCampagne.BROUILLON;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column
    private String objectifs;

    @Column
    private String publicCible;

    @Column
    private String zonesGeographiques;

    @Column
    private Integer nombreParticipants = 0;

    @Column
    private Double progression = 0.0;

    @Column
    private Double budget;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    // Relations
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference       // ← ici
    private User user;

    @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Message> messages;

    @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL)
    private List<Alerte> alertes;
}