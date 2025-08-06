package com.sante.reventionplatform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMessage typeMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioriteMessage priorite = PrioriteMessage.NORMALE;

    @Column
    private String langue = "FR";

    @Column
    private String pieceJointe;

    @Column
    private String urlImage;

    @Column
    private String urlVideo;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column
    private LocalDateTime dateEnvoi;

    @Column(nullable = false)
    private Boolean envoye = false;

    // Relations
    @ManyToOne
    @JoinColumn(name = "expediteur_id", nullable = false)
    private User expediteur;

    @ManyToOne
    @JoinColumn(name = "campagne_id")
    @JsonBackReference
    private Campagne campagne;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageDestinataire> destinataires;
}