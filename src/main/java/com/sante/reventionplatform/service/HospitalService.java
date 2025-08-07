package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Long id) {
        return hospitalRepository.findById(id);
    }

    public Hospital getHospitalWithServices(Long id) {
        return hospitalRepository.findByIdWithServices(id);
    }

    public List<Hospital> searchHospitalsByName(String name) {
        return hospitalRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Hospital> searchHospitalsByAddress(String address) {
        return hospitalRepository.findByAddressContainingIgnoreCase(address);
    }

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}