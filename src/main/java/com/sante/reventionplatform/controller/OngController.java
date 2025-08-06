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
@RequestMapping("/api/ongs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OngController {

    private final OngService ongService;

    @GetMapping
    public ResponseEntity<List<Ong>> getAllOngs() {
        return ResponseEntity.ok(ongService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ong> getOngById(@PathVariable Long id) {
        Optional<Ong> ong = ongService.findById(id);
        return ong.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Ong> getOngByUserId(@PathVariable Long userId) {
        Optional<Ong> ong = ongService.findByUserId(userId);
        return ong.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ong> createOng(@RequestBody Ong ong) {
        Ong savedOng = ongService.save(ong);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOng);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ong> updateOng(@PathVariable Long id, @RequestBody Ong ong) {
        if (!ongService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ong.setId(id);
        Ong updatedOng = ongService.update(ong);
        return ResponseEntity.ok(updatedOng);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOng(@PathVariable Long id) {
        if (!ongService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ongService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/domaine/{domaineIntervention}")
    public ResponseEntity<List<Ong>> getOngsByDomaine(@PathVariable DomaineIntervention domaineIntervention) {
        return ResponseEntity.ok(ongService.findByDomaineIntervention(domaineIntervention));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<Ong>> getOngsByRegion(@PathVariable String region) {
        return ResponseEntity.ok(ongService.findByRegion(region));
    }

    @GetMapping("/search/{nom}")
    public ResponseEntity<List<Ong>> searchOngsByNom(@PathVariable String nom) {
        return ResponseEntity.ok(ongService.findByNomOng(nom));
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Ong>> getOngsByZoneIntervention(@PathVariable String zone) {
        return ResponseEntity.ok(ongService.findByZoneIntervention(zone));
    }
}
