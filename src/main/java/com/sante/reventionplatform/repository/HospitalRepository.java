package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByNameContainingIgnoreCase(String name);

    List<Hospital> findByAddressContainingIgnoreCase(String address);

    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.services WHERE h.id = :id")
    Hospital findByIdWithServices(Long id);
}
