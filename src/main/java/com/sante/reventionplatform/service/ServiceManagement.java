package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceManagement {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public List<Service> getServicesByHospital(Long hospitalId) {
        return serviceRepository.findByHospitalId(hospitalId);
    }

    public List<Service> getServicesByHospitalWithTarifs(Long hospitalId) {
        return serviceRepository.findByHospitalIdWithTarifs(hospitalId);
    }

    public List<Service> getServicesByCategory(String category) {
        return serviceRepository.findByCategory(category);
    }

    public List<Service> searchServicesByName(String name) {
        return serviceRepository.findByNameContainingIgnoreCase(name);
    }

    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}