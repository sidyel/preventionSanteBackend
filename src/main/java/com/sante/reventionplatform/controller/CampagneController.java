package com.sante.reventionplatform.controller;

import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campagnes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CampagneController {

    private final CampagneService campagneService;

    @GetMapping
    public ResponseEntity<List<Campagne>> getAllCampagnes() {
        return ResponseEntity.ok(campagneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campagne> getCampagneById(@PathVariable Long id) {
        Optional<Campagne> campagne = campagneService.findById(id);
        return campagne.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Campagne> createCampagne(@RequestBody Campagne campagne) {
        Campagne savedCampagne = campagneService.save(campagne);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCampagne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campagne> updateCampagne(@PathVariable Long id, @RequestBody Campagne campagne) {
        if (!campagneService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        campagne.setId(id);
        Campagne updatedCampagne = campagneService.update(campagne);
        return ResponseEntity.ok(updatedCampagne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampagne(@PathVariable Long id) {
        if (!campagneService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        campagneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Campagne>> getCampagnesByStatus(@PathVariable StatusCampagne status) {
        return ResponseEntity.ok(campagneService.findByStatus(status));
    }

    @GetMapping("/type/{typeCampagne}")
    public ResponseEntity<List<Campagne>> getCampagnesByType(@PathVariable TypeCampagne typeCampagne) {
        return ResponseEntity.ok(campagneService.findByTypeCampagne(typeCampagne));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Campagne>> getCampagnesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(campagneService.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/recentes")
    public ResponseEntity<List<Campagne>> getRecentCampagnesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(campagneService.findRecentCampagnesByUser(userId));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Campagne>> getActiveCampagnes() {
        return ResponseEntity.ok(campagneService.findActiveCampaigns());
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Campagne>> getCampagnesByZone(@PathVariable String zone) {
        return ResponseEntity.ok(campagneService.findByZoneGeographique(zone));
    }

    @GetMapping("/search/{nom}")
    public ResponseEntity<List<Campagne>> searchCampagnesByNom(@PathVariable String nom) {
        return ResponseEntity.ok(campagneService.findByNom(nom));
    }

    @PutMapping("/{id}/progression")
    public ResponseEntity<Void> updateProgression(@PathVariable Long id, @RequestParam Double progression) {
        if (!campagneService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        campagneService.updateCampagneProgression(id, progression);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam StatusCampagne status) {
        if (!campagneService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        campagneService.updateCampagneStatus(id, status);
        return ResponseEntity.ok().build();
    }
}