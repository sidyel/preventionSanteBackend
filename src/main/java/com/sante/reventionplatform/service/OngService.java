package com.sante.reventionplatform.service;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OngService {

    private final OngRepository ongRepository;

    private final UserRepository userRepo;

    public List<Ong> findAll() {
        return ongRepository.findAll();
    }

    public Optional<Ong> findById(Long id) {
        return ongRepository.findById(id);
    }

    public Optional<Ong> findByUserId(Long userId) {
        return ongRepository.findByUserId(userId);
    }

    public Ong save(Ong ong) {

        User user = ong.getUser();
        User savedUser = userRepo.save(user);     // persiste l'utilisateur d'abord
        ong.setUser(savedUser);

        return ongRepository.save(ong);
    }

    public Ong update(Ong ong) {
        return ongRepository.save(ong);
    }

    public void delete(Long id) {
        ongRepository.deleteById(id);
    }

    public List<Ong> findByDomaineIntervention(DomaineIntervention domaineIntervention) {
        return ongRepository.findByDomaineIntervention(domaineIntervention);
    }

    public List<Ong> findByRegion(String region) {
        return ongRepository.findByRegion(region);
    }

    public List<Ong> findByNomOng(String nomOng) {
        return ongRepository.findByNomOngContainingIgnoreCase(nomOng);
    }

    public List<Ong> findByZoneIntervention
            (String zone) {
        return ongRepository.findByZonesInterventionContaining(zone);
    }

    public List<Ong> findByDomaineInterventionAndRegion(DomaineIntervention domaine, String region) {
        return ongRepository.findByDomaineInterventionAndRegion(domaine, region);
    }
}