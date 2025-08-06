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
@Table(name = "population_rurale")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationRurale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String profession;

    @Column
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column
    private Sexe sexe;

    @Column
    private String languePreferee;

    @Column
    private Boolean alphabetise = false;

    @Column
    private String groupeCommunautaire;

    @Column
    private String personneContact;

    @Column
    private String telephoneContact;
}
