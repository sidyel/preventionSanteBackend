package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final UserService userService;
    private final CampagneService campagneService;
    private final MessageService messageService;
    private final AlerteService alerteService;

    public Map<String, Object> getStatistiquesGenerales() {
        Map<String, Object> stats = new HashMap<>();

        // Statistiques utilisateurs
        stats.put("totalUtilisateurs", userService.findAll().size());
        stats.put("structuresSante", userService.countByTypeActeur(TypeActeur.STRUCTURE_SANTE));
        stats.put("ongs", userService.countByTypeActeur(TypeActeur.ONG));
        stats.put("populationRurale", userService.countByTypeActeur(TypeActeur.POPULATION_RURALE));

        // Statistiques campagnes
        stats.put("totalCampagnes", campagneService.findAll().size());
        stats.put("campagnesActives", campagneService.findByStatus(StatusCampagne.ACTIVE).size());
        stats.put("campagnesTerminees", campagneService.findByStatus(StatusCampagne.TERMINEE).size());

        // Statistiques messages et alertes
        stats.put("totalMessages", messageService.findAll().size());
        stats.put("totalAlertes", alerteService.findAll().size());
        stats.put("alertesActives", alerteService.findActiveAlertes().size());

        return stats;
    }

    public Map<String, Object> getStatistiquesUtilisateur(Long userId) {
        Map<String, Object> stats = new HashMap<>();

        // Campagnes de l'utilisateur
        stats.put("totalCampagnes", campagneService.findByUserId(userId).size());
        stats.put("campagnesActives", campagneService.countByUserIdAndStatus(userId, StatusCampagne.ACTIVE));
        stats.put("campagnesTerminees", campagneService.countByUserIdAndStatus(userId, StatusCampagne.TERMINEE));

        // Messages
        stats.put("messagesEnvoyes", messageService.countSentMessages(userId));
        stats.put("messagesNonLus", messageService.countUnreadMessages(userId));

        // Alertes
        stats.put("alertesNonLues", alerteService.countUnreadAlertes(userId));

        return stats;
    }
}