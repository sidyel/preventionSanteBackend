package com.sante.reventionplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@RestController
@RequestMapping("/api/structures-sante")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StructureSanteController {

    private final StructureSanteService structureSanteService;

    @GetMapping
    public ResponseEntity<List<StructureSante>> getAllStructures() {
        return ResponseEntity.ok(structureSanteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureSante> getStructureById(@PathVariable Long id) {
        Optional<StructureSante> structure = structureSanteService.findById(id);
        return structure.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<StructureSante> getStructureByUserId(@PathVariable Long userId) {
        Optional<StructureSante> structure = structureSanteService.findByUserId(userId);
        return structure.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StructureSante> createStructure(@RequestBody StructureSante structure) {
        StructureSante savedStructure = structureSanteService.save(structure);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStructure);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StructureSante> updateStructure(@PathVariable Long id, @RequestBody StructureSante structure) {
        if (!structureSanteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        structure.setId(id);
        StructureSante updatedStructure = structureSanteService.update(structure);
        return ResponseEntity.ok(updatedStructure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStructure(@PathVariable Long id) {
        if (!structureSanteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        structureSanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{typeStructure}")
    public ResponseEntity<List<StructureSante>> getStructuresByType(@PathVariable TypeStructure typeStructure) {
        return ResponseEntity.ok(structureSanteService.findByTypeStructure(typeStructure));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<StructureSante>> getStructuresByRegion(@PathVariable String region) {
        return ResponseEntity.ok(structureSanteService.findByRegion(region));
    }

    @GetMapping("/search/{nom}")
    public ResponseEntity<List<StructureSante>> searchStructuresByNom(@PathVariable String nom) {
        return ResponseEntity.ok(structureSanteService.findByNomStructure(nom));
    }
}