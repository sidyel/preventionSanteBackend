package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {
    List<Tarif> findByServiceId(Long serviceId);
    List<Tarif> findByProcedureNameContainingIgnoreCase(String procedureName);
    List<Tarif> findByServiceHospitalId(Long hospitalId);
}