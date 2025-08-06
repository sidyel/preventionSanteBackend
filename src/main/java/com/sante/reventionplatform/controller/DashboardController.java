package com.sante.reventionplatform.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final CampagneService campagneService;
    private final MessageService messageService;
    private final AlerteService alerteService;
    private final NotificationService notificationService;
    private final StatsService statsService;

    @GetMapping("/ong/{userId}")
    public ResponseEntity<Map<String, Object>> getDashboardOng(@PathVariable Long userId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Statistiques
        dashboard.put("stats", statsService.getStatistiquesUtilisateur(userId));

        // Campagnes récentes
        List<Campagne> recentCampaigns = campagneService.findRecentCampagnesByUser(userId);
        dashboard.put("recentCampaigns", recentCampaigns.size() > 5 ? recentCampaigns.subList(0, 5) : recentCampaigns);

        // Messages non lus
        dashboard.put("unreadMessages", messageService.countUnreadMessages(userId));

        // Alertes non lues
        dashboard.put("unreadAlertes", alerteService.countUnreadAlertes(userId));

        // Notifications non lues
        dashboard.put("unreadNotifications", notificationService.countUnreadNotifications(userId));

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/structure-sante/{userId}")
    public ResponseEntity<Map<String, Object>> getDashboardStructureSante(@PathVariable Long userId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Statistiques
        dashboard.put("stats", statsService.getStatistiquesUtilisateur(userId));

        // Campagnes récentes
        List<Campagne> recentCampaigns = campagneService.findRecentCampagnesByUser(userId);
        dashboard.put("recentCampaigns", recentCampaigns.size() > 5 ? recentCampaigns.subList(0, 5) : recentCampaigns);

        // Messages non lus
        dashboard.put("unreadMessages", messageService.countUnreadMessages(userId));

        // Alertes non lues
        dashboard.put("unreadAlertes", alerteService.countUnreadAlertes(userId));

        // Notifications non lues
        dashboard.put("unreadNotifications", notificationService.countUnreadNotifications(userId));

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/population/{userId}")
    public ResponseEntity<Map<String, Object>> getDashboardPopulation(@PathVariable Long userId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Messages reçus non lus
        dashboard.put("unreadMessages", messageService.countUnreadMessages(userId));

        // Alertes reçues non lues
        dashboard.put("unreadAlertes", alerteService.countUnreadAlertes(userId));

        // Notifications non lues
        dashboard.put("unreadNotifications", notificationService.countUnreadNotifications(userId));

        // Messages récents
        List<Message> recentMessages = messageService.findByDestinataireId(userId);
        dashboard.put("recentMessages", recentMessages.size() > 10 ? recentMessages.subList(0, 10) : recentMessages);

        // Alertes récentes
        List<Alerte> recentAlertes = alerteService.findByDestinataireId(userId);
        dashboard.put("recentAlertes", recentAlertes.size() > 10 ? recentAlertes.subList(0, 10) : recentAlertes);

        return ResponseEntity.ok(dashboard);
    }
}