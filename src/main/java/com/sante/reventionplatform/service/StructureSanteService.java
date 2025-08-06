package com.sante.reventionplatform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
@Service
@RequiredArgsConstructor
@Transactional
public class StructureSanteService {

    private final StructureSanteRepository structureSanteRepository;

    public List<StructureSante> findAll() {
        return structureSanteRepository.findAll();
    }

    public Optional<StructureSante> findById(Long id) {
        return structureSanteRepository.findById(id);
    }

    public Optional<StructureSante> findByUserId(Long userId) {
        return structureSanteRepository.findByUserId(userId);
    }

    public StructureSante save(StructureSante structureSante) {
        return structureSanteRepository.save(structureSante);
    }

    public StructureSante update(StructureSante structureSante) {
        return structureSanteRepository.save(structureSante);
    }

    public void delete(Long id) {
        structureSanteRepository.deleteById(id);
    }

    public List<StructureSante> findByTypeStructure(TypeStructure typeStructure) {
        return structureSanteRepository.findByTypeStructure(typeStructure);
    }

    public List<StructureSante> findByRegion(String region) {
        return structureSanteRepository.findByRegion(region);
    }

    public List<StructureSante> findByNomStructure(String nomStructure) {
        return structureSanteRepository.findByNomStructureContainingIgnoreCase(nomStructure);
    }

    public List<StructureSante> findByTypeStructureAndRegion(TypeStructure typeStructure, String region) {
        return structureSanteRepository.findByTypeStructureAndRegion(typeStructure, region);
    }
}