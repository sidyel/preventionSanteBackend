package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.Campagne;
import com.sante.reventionplatform.entity.StatusCampagne;
import com.sante.reventionplatform.entity.TypeCampagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {
    List<Campagne> findByStatus(StatusCampagne status);
    List<Campagne> findByTypeCampagne(TypeCampagne typeCampagne);
    List<Campagne> findByUserId(Long userId);
    List<Campagne> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT c FROM Campagne c WHERE c.dateDebut <= :now AND c.dateFin >= :now AND c.status = :status")
    List<Campagne> findActiveCampaigns(@Param("now") LocalDateTime now, @Param("status") StatusCampagne status);

    @Query("SELECT c FROM Campagne c WHERE c.user.id = :userId ORDER BY c.dateCreation DESC")
    List<Campagne> findByUserIdOrderByDateCreationDesc(@Param("userId") Long userId);

    @Query("SELECT c FROM Campagne c WHERE c.zonesGeographiques LIKE %:zone%")
    List<Campagne> findByZonesGeographiquesContaining(@Param("zone") String zone);

    @Query("SELECT COUNT(c) FROM Campagne c WHERE c.user.id = :userId AND c.status = :status")
    Long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") StatusCampagne status);

    @Query("SELECT c FROM Campagne c WHERE c.dateFin < :now AND c.status = 'ACTIVE'")
    List<Campagne> findExpiredActiveCampaigns(@Param("now") LocalDateTime now);
}
