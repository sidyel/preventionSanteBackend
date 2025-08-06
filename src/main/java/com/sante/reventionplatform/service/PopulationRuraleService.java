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
public class PopulationRuraleService {

    private final PopulationRuraleRepository populationRuraleRepository;

    public List<PopulationRurale> findAll() {
        return populationRuraleRepository.findAll();
    }

    public Optional<PopulationRurale> findById(Long id) {
        return populationRuraleRepository.findById(id);
    }

    public Optional<PopulationRurale> findByUserId(Long userId) {
        return populationRuraleRepository.findByUserId(userId);
    }

    public PopulationRurale save(PopulationRurale populationRurale) {
        return populationRuraleRepository.save(populationRurale);
    }

    public PopulationRurale update(PopulationRurale populationRurale) {
        return populationRuraleRepository.save(populationRurale);
    }

    public void delete(Long id) {
        populationRuraleRepository.deleteById(id);
    }

    public List<PopulationRurale> findByRegion(String region) {
        return populationRuraleRepository.findByRegion(region);
    }

    public List<PopulationRurale> findByCommune(String commune) {
        return populationRuraleRepository.findByCommune(commune);
    }

    public List<PopulationRurale> findByVillage(String village) {
        return populationRuraleRepository.findByVillage(village);
    }

    public List<PopulationRurale> findBySexe(Sexe sexe) {
        return populationRuraleRepository.findBySexe(sexe);
    }

    public List<PopulationRurale> findByLanguePreferee(String languePreferee) {
        return populationRuraleRepository.findByLanguePreferee(languePreferee);
    }

    public List<PopulationRurale> findByAlphabetise(Boolean alphabetise) {
        return populationRuraleRepository.findByAlphabetise(alphabetise);
    }

    public List<PopulationRurale> findByGroupeCommunautaire(String groupeCommunautaire) {
        return populationRuraleRepository.findByGroupeCommunautaire(groupeCommunautaire);
    }

    public List<PopulationRurale> findByAgeBetween(Integer ageMin, Integer ageMax) {
        return populationRuraleRepository.findByAgeBetween(ageMin, ageMax);
    }
}
