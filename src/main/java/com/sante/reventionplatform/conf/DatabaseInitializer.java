package com.sante.reventionplatform.conf;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final OngService ongService;
    private final StructureSanteService structureSanteService;
    private final PopulationRuraleService populationRuraleService;
    private final CampagneService campagneService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.findAll().isEmpty()) {
            initializeTestData();
        }
    }

    private void initializeTestData() {
        // Créer des utilisateurs de test
        User userOng = new User();
        userOng.setNom("Diallo");
        userOng.setPrenom("Amadou");
        userOng.setEmail("amadou@ong-sante.org");
        userOng.setTelephone("+221701234567");
        userOng.setTypeActeur(TypeActeur.ONG);
        userOng.setRegion("Dakar");
        userOng.setCommune("Dakar-Plateau");
        userOng = userService.save(userOng);

        // Créer l'ONG associée
        Ong ong = new Ong();
        ong.setUser(userOng);
        ong.setNomOng("ONG Santé pour Tous");
        ong.setNumeroEnregistrement("ONG-2024-001");
        ong.setDomaineIntervention(DomaineIntervention.SANTE_PUBLIQUE);
        ong.setMission("Améliorer l'accès aux soins de santé en milieu rural");
        ong.setVision("Une société où chacun a accès à des soins de qualité");
        ong.setNombreEmployes(25);
        ong.setZonesIntervention("Dakar, Thiès, Diourbel");
        ongService.save(ong);

        // Créer un utilisateur structure de santé
        User userStructure = new User();
        userStructure.setNom("Fall");
        userStructure.setPrenom("Fatou");
        userStructure.setEmail("fatou@hopital-dakar.sn");
        userStructure.setTelephone("+221702345678");
        userStructure.setTypeActeur(TypeActeur.STRUCTURE_SANTE);
        userStructure.setRegion("Dakar");
        userStructure.setCommune("Dakar-Plateau");
        userStructure = userService.save(userStructure);

        // Créer la structure de santé associée
        StructureSante structure = new StructureSante();
        structure.setUser(userStructure);
        structure.setNomStructure("Hôpital Principal de Dakar");
        structure.setTypeStructure(TypeStructure.HOPITAL);
        structure.setNumeroAgrement("HOSP-2024-001");
        structure.setDescription("Hôpital de référence nationale");
        structure.setAdresse("Avenue Cheikh Anta Diop, Dakar");
        structure.setCapaciteAccueil(500);
        structure.setSpecialites("Médecine générale, Chirurgie, Pédiatrie, Gynécologie");
        structureSanteService.save(structure);

        // Créer un utilisateur population rurale
        User userPopulation = new User();
        userPopulation.setNom("Ndiaye");
        userPopulation.setPrenom("Moussa");
        userPopulation.setEmail("moussa@village.sn");
        userPopulation.setTelephone("+221703456789");
        userPopulation.setTypeActeur(TypeActeur.POPULATION_RURALE);
        userPopulation.setRegion("Thiès");
        userPopulation.setCommune("Thiès-Nord");
        userPopulation.setVillage("Keur Moussa");
        userPopulation = userService.save(userPopulation);

        // Créer le profil population rurale associé
        PopulationRurale population = new PopulationRurale();
        population.setUser(userPopulation);
        population.setProfession("Agriculteur");
        population.setAge(35);
        population.setSexe(Sexe.MASCULIN);
        population.setLanguePreferee("Wolof");
        population.setAlphabetise(false);
        population.setGroupeCommunautaire("Association des Agriculteurs");
        population.setPersonneContact("Aminata Ndiaye");
        population.setTelephoneContact("+221704567890");
        populationRuraleService.save(population);

        // Créer des campagnes de test
        Campagne campagne1 = new Campagne();
        campagne1.setNom("Campagne Prévention Paludisme 2024");
        campagne1.setDescription("Sensibilisation sur la prévention du paludisme en milieu rural");
        campagne1.setTypeCampagne(TypeCampagne.PREVENTION_PALUDISME);
        campagne1.setStatus(StatusCampagne.ACTIVE);
        campagne1.setDateDebut(LocalDateTime.now().minusDays(30));
        campagne1.setDateFin(LocalDateTime.now().plusDays(60));
        campagne1.setObjectifs("Réduire l'incidence du paludisme de 30%");
        campagne1.setPublicCible("Population rurale, enfants de moins de 5 ans");
        campagne1.setZonesGeographiques("Thiès, Diourbel, Fatick");
        campagne1.setNombreParticipants(2500);
        campagne1.setProgression(75.0);
        campagne1.setBudget(15000000.0);
        campagne1.setUser(userOng);
        campagneService.save(campagne1);

        Campagne campagne2 = new Campagne();
        campagne2.setNom("Sensibilisation Hygiène et Assainissement");
        campagne2.setDescription("Formation sur les bonnes pratiques d'hygiène et d'assainissement");
        campagne2.setTypeCampagne(TypeCampagne.HYGIENE_EAU);
        campagne2.setStatus(StatusCampagne.TERMINEE);
        campagne2.setDateDebut(LocalDateTime.now().minusDays(90));
        campagne2.setDateFin(LocalDateTime.now().minusDays(10));
        campagne2.setObjectifs("Former 1000 personnes aux bonnes pratiques d'hygiène");
        campagne2.setPublicCible("Femmes en âge de procréer, leaders communautaires");
        campagne2.setZonesGeographiques("Dakar, Rufisque");
        campagne2.setNombreParticipants(1200);
        campagne2.setProgression(100.0);
        campagne2.setBudget(8000000.0);
        campagne2.setUser(userStructure);
        campagneService.save(campagne2);

        Campagne campagne3 = new Campagne();
        campagne3.setNom("Formation Agents de Santé Communautaire");
        campagne3.setDescription("Renforcement des capacités des agents de santé communautaire");
        campagne3.setTypeCampagne(TypeCampagne.EDUCATION_SANITAIRE);
        campagne3.setStatus(StatusCampagne.BROUILLON);
        campagne3.setDateDebut(LocalDateTime.now().plusDays(15));
        campagne3.setDateFin(LocalDateTime.now().plusDays(105));
        campagne3.setObjectifs("Former 50 agents de santé communautaire");
        campagne3.setPublicCible("Agents de santé communautaire");
        campagne3.setZonesGeographiques("Kaolack, Tambacounda");
        campagne3.setNombreParticipants(0);
        campagne3.setProgression(25.0);
        campagne3.setBudget(12000000.0);
        campagne3.setUser(userOng);
        campagneService.save(campagne3);

        System.out.println("Données de test initialisées avec succès !");
        System.out.println("Utilisateurs créés:");
        System.out.println("- ONG: " + userOng.getEmail());
        System.out.println("- Structure Santé: " + userStructure.getEmail());
        System.out.println("- Population Rurale: " + userPopulation.getEmail());
        System.out.println("Campagnes créées: 3");
        System.out.println("Documentation API disponible sur: http://localhost:8080/swagger-ui.html");
    }
}