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
@RequestMapping("/api/population-rurale")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PopulationRuraleController {

    private final PopulationRuraleService populationRuraleService;

    @GetMapping
    public ResponseEntity<List<PopulationRurale>> getAllPopulation() {
        return ResponseEntity.ok(populationRuraleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PopulationRurale> getPopulationById(@PathVariable Long id) {
        Optional<PopulationRurale> population = populationRuraleService.findById(id);
        return population.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PopulationRurale> getPopulationByUserId(@PathVariable Long userId) {
        Optional<PopulationRurale> population = populationRuraleService.findByUserId(userId);
        return population.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PopulationRurale> createPopulation(@RequestBody PopulationRurale population) {
        PopulationRurale savedPopulation = populationRuraleService.save(population);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPopulation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PopulationRurale> updatePopulation(@PathVariable Long id, @RequestBody PopulationRurale population) {
        if (!populationRuraleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        population.setId(id);
        PopulationRurale updatedPopulation = populationRuraleService.update(population);
        return ResponseEntity.ok(updatedPopulation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePopulation(@PathVariable Long id) {
        if (!populationRuraleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        populationRuraleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<PopulationRurale>> getPopulationByRegion(@PathVariable String region) {
        return ResponseEntity.ok(populationRuraleService.findByRegion(region));
    }

    @GetMapping("/commune/{commune}")
    public ResponseEntity<List<PopulationRurale>> getPopulationByCommune(@PathVariable String commune) {
        return ResponseEntity.ok(populationRuraleService.findByCommune(commune));
    }

    @GetMapping("/village/{village}")
    public ResponseEntity<List<PopulationRurale>> getPopulationByVillage(@PathVariable String village) {
        return ResponseEntity.ok(populationRuraleService.findByVillage(village));
    }

    @GetMapping("/sexe/{sexe}")
    public ResponseEntity<List<PopulationRurale>> getPopulationBySexe(@PathVariable Sexe sexe) {
        return ResponseEntity.ok(populationRuraleService.findBySexe(sexe));
    }

    @GetMapping("/langue/{langue}")
    public ResponseEntity<List<PopulationRurale>> getPopulationByLangue(@PathVariable String langue) {
        return ResponseEntity.ok(populationRuraleService.findByLanguePreferee(langue));
    }

    @GetMapping("/alphabetise/{alphabetise}")
    public ResponseEntity<List<PopulationRurale>> getPopulationByAlphabetise(@PathVariable Boolean alphabetise) {
        return ResponseEntity.ok(populationRuraleService.findByAlphabetise(alphabetise));
    }

    @GetMapping("/age")
    public ResponseEntity<List<PopulationRurale>> getPopulationByAge(
            @RequestParam Integer ageMin,
            @RequestParam Integer ageMax) {
        return ResponseEntity.ok(populationRuraleService.findByAgeBetween(ageMin, ageMax));
    }
}