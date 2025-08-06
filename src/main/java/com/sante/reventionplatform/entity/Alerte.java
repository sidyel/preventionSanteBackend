package com.sante.reventionplatform.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(name = "alertes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAlerte typeAlerte;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NiveauUrgence niveauUrgence;

    @Column
    private String zoneAffectee;

    @Column
    private String mesuresPreventives;

    @Column
    private String consignesSuivre;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column
    private LocalDateTime dateExpiration;

    @Column(nullable = false)
    private Boolean active = true;

    // Relations
    @ManyToOne
    @JoinColumn(name = "expediteur_id", nullable = false)
    private User expediteur;

    @ManyToOne
    @JoinColumn(name = "campagne_id")
    private Campagne campagne;

    @OneToMany(mappedBy = "alerte", cascade = CascadeType.ALL)
    private List<AlerteDestinataire> destinataires;
}