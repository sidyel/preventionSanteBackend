package com.sante.reventionplatform.controller;

import com.sante.reventionplatform.dto.HospitalDTO;
import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalDTO> hospitalDTOs = hospitals.stream()
                .map(HospitalDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hospitalDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) {
        Optional<Hospital> hospital = hospitalService.getHospitalById(id);
        if (hospital.isPresent()) {
            return ResponseEntity.ok(new HospitalDTO(hospital.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/with-services")
    public ResponseEntity<HospitalDTO> getHospitalWithServices(@PathVariable Long id) {
        Hospital hospital = hospitalService.getHospitalWithServices(id);
        if (hospital != null) {
            return ResponseEntity.ok(new HospitalDTO(hospital));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<HospitalDTO>> searchHospitals(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {

        List<Hospital> hospitals;
        if (name != null && !name.isEmpty()) {
            hospitals = hospitalService.searchHospitalsByName(name);
        } else if (address != null && !address.isEmpty()) {
            hospitals = hospitalService.searchHospitalsByAddress(address);
        } else {
            hospitals = hospitalService.getAllHospitals();
        }

        List<HospitalDTO> hospitalDTOs = hospitals.stream()
                .map(HospitalDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hospitalDTOs);
    }

    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody Hospital hospital) {
        Hospital savedHospital = hospitalService.saveHospital(hospital);
        return ResponseEntity.ok(new HospitalDTO(savedHospital));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@PathVariable Long id, @RequestBody Hospital hospitalDetails) {
        Optional<Hospital> hospitalOpt = hospitalService.getHospitalById(id);
        if (hospitalOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hospital hospital = hospitalOpt.get();
        hospital.setName(hospitalDetails.getName());
        hospital.setAddress(hospitalDetails.getAddress());
        hospital.setPhone(hospitalDetails.getPhone());
        hospital.setEmail(hospitalDetails.getEmail());
        hospital.setDescription(hospitalDetails.getDescription());

        Hospital updatedHospital = hospitalService.saveHospital(hospital);
        return ResponseEntity.ok(new HospitalDTO(updatedHospital));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok().build();
    }
}