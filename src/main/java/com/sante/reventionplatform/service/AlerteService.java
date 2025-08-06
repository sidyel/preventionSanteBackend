package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.AlerteDestinataireRepository;
import com.sante.reventionplatform.repository.AlerteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlerteService {

    private final AlerteRepository alerteRepository;
    private final AlerteDestinataireRepository alerteDestinataireRepository;
    private final NotificationService notificationService;

    public List<Alerte> findAll() {
        return alerteRepository.findAll();
    }

    public Optional<Alerte> findById(Long id) {
        return alerteRepository.findById(id);
    }

    public Alerte save(Alerte alerte) {
        return alerteRepository.save(alerte);
    }

    public Alerte update(Alerte alerte) {
        return alerteRepository.save(alerte);
    }

    public void delete(Long id) {
        alerteRepository.deleteById(id);
    }

    public List<Alerte> findByExpediteurId(Long expediteurId) {
        return alerteRepository.findByExpediteurIdOrderByDateCreationDesc(expediteurId);
    }

    public List<Alerte> findByDestinataireId(Long destinataireId) {
        return alerteRepository.findByDestinataireId(destinataireId);
    }

    public List<Alerte> findUnreadAlertesByDestinataireId(Long destinataireId) {
        return alerteRepository.findUnreadAlertesByDestinataireId(destinataireId);
    }

    public List<Alerte> findActiveAlertes() {
        return alerteRepository.findByActiveTrue();
    }

    public List<Alerte> findByTypeAlerte(TypeAlerte typeAlerte) {
        return alerteRepository.findByTypeAlerte(typeAlerte);
    }

    public List<Alerte> findByNiveauUrgence(NiveauUrgence niveauUrgence) {
        return alerteRepository.findByNiveauUrgence(niveauUrgence);
    }

    public List<Alerte> findActiveAlertesByZone(String zone) {
        return alerteRepository.findActiveAlertesByZone(zone);
    }

    public Alerte diffuserAlerte(Alerte alerte, List<User> destinataires) {
        alerte.setDateCreation(LocalDateTime.now());
        alerte.setActive(true);
        Alerte alerteSauvee = alerteRepository.save(alerte);

        // Créer les relations alerte-destinataires
        for (User destinataire : destinataires) {
            AlerteDestinataire ad = new AlerteDestinataire();
            ad.setAlerte(alerteSauvee);
            ad.setDestinataire(destinataire);
            ad.setDateReception(LocalDateTime.now());
            alerteDestinataireRepository.save(ad);

            // Créer une notification urgente
            notificationService.creerNotificationAlerte(destinataire, alerteSauvee);
        }

        return alerteSauvee;
    }

    public void marquerCommeLu(Long alerteId, Long destinataireId) {
        Optional<AlerteDestinataire> ad = alerteDestinataireRepository
                .findByAlerteIdAndDestinataireId(alerteId, destinataireId);
        if (ad.isPresent() && !ad.get().getLu()) {
            ad.get().setLu(true);
            ad.get().setDateLecture(LocalDateTime.now());
            alerteDestinataireRepository.save(ad.get());
        }
    }

    public void accuserReception(Long alerteId, Long destinataireId) {
        Optional<AlerteDestinataire> ad = alerteDestinataireRepository
                .findByAlerteIdAndDestinataireId(alerteId, destinataireId);
        if (ad.isPresent() && !ad.get().getAccuse()) {
            ad.get().setAccuse(true);
            ad.get().setDateAccuse(LocalDateTime.now());
            alerteDestinataireRepository.save(ad.get());
        }
    }

    public void desactiverAlerte(Long alerteId) {
        Optional<Alerte> alerte = alerteRepository.findById(alerteId);
        if (alerte.isPresent()) {
            alerte.get().setActive(false);
            alerteRepository.save(alerte.get());
        }
    }

    public List<Alerte> findExpiredActiveAlertes() {
        return alerteRepository.findExpiredActiveAlertes(LocalDateTime.now());
    }

    public Long countUnreadAlertes(Long destinataireId) {
        return alerteDestinataireRepository.countUnreadByDestinataireId(destinataireId);
    }

    public Long countAccusedReceptions(Long alerteId) {
        return alerteDestinataireRepository.countAccusedByAlerteId(alerteId);
    }
}
