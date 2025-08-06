package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.CampagneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CampagneService {

    private final CampagneRepository campagneRepository;

    public List<Campagne> findAll() {
        return campagneRepository.findAll();
    }

    public Optional<Campagne> findById(Long id) {
        return campagneRepository.findById(id);
    }

    public Campagne save(Campagne campagne) {
        return campagneRepository.save(campagne);
    }

    public Campagne update(Campagne campagne) {
        return campagneRepository.save(campagne);
    }

    public void delete(Long id) {
        campagneRepository.deleteById(id);
    }

    public List<Campagne> findByStatus(StatusCampagne status) {
        return campagneRepository.findByStatus(status);
    }

    public List<Campagne> findByTypeCampagne(TypeCampagne typeCampagne) {
        return campagneRepository.findByTypeCampagne(typeCampagne);
    }

    public List<Campagne> findByUserId(Long userId) {
        return campagneRepository.findByUserId(userId);
    }

    public List<Campagne> findRecentCampagnesByUser(Long userId) {
        return campagneRepository.findByUserIdOrderByDateCreationDesc(userId);
    }

    public List<Campagne> findActiveCampaigns() {
        return campagneRepository.findActiveCampaigns(LocalDateTime.now(), StatusCampagne.ACTIVE);
    }

    public List<Campagne> findByZoneGeographique(String zone) {
        return campagneRepository.findByZonesGeographiquesContaining(zone);
    }

    public List<Campagne> findByNom(String nom) {
        return campagneRepository.findByNomContainingIgnoreCase(nom);
    }

    public Long countByUserIdAndStatus(Long userId, StatusCampagne status) {
        return campagneRepository.countByUserIdAndStatus(userId, status);
    }

    public void updateCampagneProgression(Long campagneId, Double progression) {
        Optional<Campagne> campagne = campagneRepository.findById(campagneId);
        if (campagne.isPresent()) {
            campagne.get().setProgression(progression);
            campagneRepository.save(campagne.get());
        }
    }

    public void updateCampagneStatus(Long campagneId, StatusCampagne status) {
        Optional<Campagne> campagne = campagneRepository.findById(campagneId);
        if (campagne.isPresent()) {
            campagne.get().setStatus(status);
            campagneRepository.save(campagne.get());
        }
    }

    public List<Campagne> findExpiredActiveCampaigns() {
        return campagneRepository.findExpiredActiveCampaigns(LocalDateTime.now());
    }
}