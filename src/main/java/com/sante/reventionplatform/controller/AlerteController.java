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
@RequestMapping("/api/alertes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlerteController {

    private final AlerteService alerteService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Alerte>> getAllAlertes() {
        return ResponseEntity.ok(alerteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerte> getAlerteById(@PathVariable Long id) {
        Optional<Alerte> alerte = alerteService.findById(id);
        return alerte.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alerte> createAlerte(@RequestBody Alerte alerte) {
        Alerte savedAlerte = alerteService.save(alerte);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerte> updateAlerte(@PathVariable Long id, @RequestBody Alerte alerte) {
        if (!alerteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        alerte.setId(id);
        Alerte updatedAlerte = alerteService.update(alerte);
        return ResponseEntity.ok(updatedAlerte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerte(@PathVariable Long id) {
        if (!alerteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        alerteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expediteur/{expediteurId}")
    public ResponseEntity<List<Alerte>> getAlertesByExpediteur(@PathVariable Long expediteurId) {
        return ResponseEntity.ok(alerteService.findByExpediteurId(expediteurId));
    }

    @GetMapping("/destinataire/{destinataireId}")
    public ResponseEntity<List<Alerte>> getAlertesByDestinataire(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(alerteService.findByDestinataireId(destinataireId));
    }

    @GetMapping("/destinataire/{destinataireId}/non-lues")
    public ResponseEntity<List<Alerte>> getUnreadAlertes(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(alerteService.findUnreadAlertesByDestinataireId(destinataireId));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Alerte>> getActiveAlertes() {
        return ResponseEntity.ok(alerteService.findActiveAlertes());
    }

    @GetMapping("/type/{typeAlerte}")
    public ResponseEntity<List<Alerte>> getAlertesByType(@PathVariable TypeAlerte typeAlerte) {
        return ResponseEntity.ok(alerteService.findByTypeAlerte(typeAlerte));
    }

    @GetMapping("/urgence/{niveauUrgence}")
    public ResponseEntity<List<Alerte>> getAlertesByUrgence(@PathVariable NiveauUrgence niveauUrgence) {
        return ResponseEntity.ok(alerteService.findByNiveauUrgence(niveauUrgence));
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Alerte>> getAlertesByZone(@PathVariable String zone) {
        return ResponseEntity.ok(alerteService.findActiveAlertesByZone(zone));
    }

    @PostMapping("/{id}/diffuser")
    public ResponseEntity<Alerte> diffuserAlerte(@PathVariable Long id, @RequestBody List<Long> destinataireIds) {
        Optional<Alerte> alerteOpt = alerteService.findById(id);
        if (!alerteOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<User> destinataires = destinataireIds.stream()
                .map(userService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        Alerte alerteDiffused = alerteService.diffuserAlerte(alerteOpt.get(), destinataires);
        return ResponseEntity.ok(alerteDiffused);
    }

    @PutMapping("/{alerteId}/marquer-lu/{destinataireId}")
    public ResponseEntity<Void> marquerCommeLu(@PathVariable Long alerteId, @PathVariable Long destinataireId) {
        alerteService.marquerCommeLu(alerteId, destinataireId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{alerteId}/accuser-reception/{destinataireId}")
    public ResponseEntity<Void> accuserReception(@PathVariable Long alerteId, @PathVariable Long destinataireId) {
        alerteService.accuserReception(alerteId, destinataireId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverAlerte(@PathVariable Long id) {
        if (!alerteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        alerteService.desactiverAlerte(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/destinataire/{destinataireId}/count-non-lues")
    public ResponseEntity<Long> countUnreadAlertes(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(alerteService.countUnreadAlertes(destinataireId));
    }

    @GetMapping("/{alerteId}/count-accuses")
    public ResponseEntity<Long> countAccusedReceptions(@PathVariable Long alerteId) {
        return ResponseEntity.ok(alerteService.countAccusedReceptions(alerteId));
    }
}
