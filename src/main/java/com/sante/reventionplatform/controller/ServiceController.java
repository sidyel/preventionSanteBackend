package com.sante.reventionplatform.controller;

import com.sante.reventionplatform.dto.ServiceDTO;
import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.ServiceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceManagement serviceManagement;

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<Service> services = serviceManagement.getAllServices();
        List<ServiceDTO> serviceDTOs = services.stream()
                .map(ServiceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        Optional<Service> service = serviceManagement.getServiceById(id);
        if (service.isPresent()) {
            return ResponseEntity.ok(new ServiceDTO(service.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<ServiceDTO>> getServicesByHospital(@PathVariable Long hospitalId) {
        List<Service> services = serviceManagement.getServicesByHospitalWithTarifs(hospitalId);
        List<ServiceDTO> serviceDTOs = services.stream()
                .map(ServiceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceDTO>> getServicesByCategory(@PathVariable String category) {
        List<Service> services = serviceManagement.getServicesByCategory(category);
        List<ServiceDTO> serviceDTOs = services.stream()
                .map(ServiceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceDTO>> searchServices(@RequestParam String name) {
        List<Service> services = serviceManagement.searchServicesByName(name);
        List<ServiceDTO> serviceDTOs = services.stream()
                .map(ServiceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@RequestBody Service service) {
        Service savedService = serviceManagement.saveService(service);
        return ResponseEntity.ok(new ServiceDTO(savedService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceManagement.deleteService(id);
        return ResponseEntity.ok().build();
    }
}