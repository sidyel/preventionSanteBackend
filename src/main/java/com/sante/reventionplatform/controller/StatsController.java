package com.sante.reventionplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/generales")
    public ResponseEntity<Map<String, Object>> getStatistiquesGenerales() {
        return ResponseEntity.ok(statsService.getStatistiquesGenerales());
    }

    @GetMapping("/utilisateur/{userId}")
    public ResponseEntity<Map<String, Object>> getStatistiquesUtilisateur(@PathVariable Long userId) {
        return ResponseEntity.ok(statsService.getStatistiquesUtilisateur(userId));
    }
}
