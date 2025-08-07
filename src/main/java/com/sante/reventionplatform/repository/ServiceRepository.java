package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByHospitalId(Long hospitalId);
    List<Service> findByCategory(String category);
    List<Service> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Service s LEFT JOIN FETCH s.tarifs WHERE s.hospital.id = :hospitalId")
    List<Service> findByHospitalIdWithTarifs(Long hospitalId);
    // Nouvelle méthode pour l'initialisation
    @Query("SELECT s FROM Service s WHERE s.hospital.id = :hospitalId AND s.name LIKE %:name%")
    Service findByHospitalIdAndNameContaining(Long hospitalId, String name);
}