package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.DomaineIntervention;
import com.sante.reventionplatform.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
    Optional<Ong> findByUserId(Long userId);
    List<Ong> findByDomaineIntervention(DomaineIntervention domaineIntervention);
    List<Ong> findByNomOngContainingIgnoreCase(String nomOng);
    Optional<Ong> findByNumeroEnregistrement(String numeroEnregistrement);

    @Query("SELECT o FROM Ong o WHERE o.user.region = :region")
    List<Ong> findByRegion(@Param("region") String region);

    @Query("SELECT o FROM Ong o WHERE o.domaineIntervention = :domaine AND o.user.region = :region")
    List<Ong> findByDomaineInterventionAndRegion(@Param("domaine") DomaineIntervention domaine, @Param("region") String region);

    @Query("SELECT o FROM Ong o WHERE o.zonesIntervention LIKE %:zone%")
    List<Ong> findByZonesInterventionContaining(@Param("zone") String zone);
}
