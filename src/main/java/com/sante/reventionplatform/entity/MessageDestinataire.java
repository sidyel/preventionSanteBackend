package com.sante.reventionplatform.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "message_destinataires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDestinataire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "destinataire_id", nullable = false)
    @JsonBackReference
    private User destinataire;

    @Column(nullable = false)
    private Boolean lu = false;

    @Column
    private LocalDateTime dateLecture;

    @Column
    private LocalDateTime dateReception = LocalDateTime.now();
}