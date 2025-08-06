package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.Alerte;
import com.sante.reventionplatform.entity.NiveauUrgence;
import com.sante.reventionplatform.entity.TypeAlerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {
    List<Alerte> findByExpediteurId(Long expediteurId);
    List<Alerte> findByTypeAlerte(TypeAlerte typeAlerte);
    List<Alerte> findByNiveauUrgence(NiveauUrgence niveauUrgence);
    List<Alerte> findByActiveTrue();
    List<Alerte> findByCampagneId(Long campagneId);

    @Query("SELECT a FROM Alerte a WHERE a.expediteur.id = :expediteurId ORDER BY a.dateCreation DESC")
    List<Alerte> findByExpediteurIdOrderByDateCreationDesc(@Param("expediteurId") Long expediteurId);

    @Query("SELECT a FROM Alerte a JOIN a.destinataires ad WHERE ad.destinataire.id = :destinataireId")
    List<Alerte> findByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT a FROM Alerte a JOIN a.destinataires ad WHERE ad.destinataire.id = :destinataireId AND ad.lu = false")
    List<Alerte> findUnreadAlertesByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT a FROM Alerte a WHERE a.zoneAffectee LIKE %:zone% AND a.active = true")
    List<Alerte> findActiveAlertesByZone(@Param("zone") String zone);

    @Query("SELECT a FROM Alerte a WHERE a.dateExpiration < :now AND a.active = true")
    List<Alerte> findExpiredActiveAlertes(@Param("now") LocalDateTime now);

    @Query("SELECT a FROM Alerte a WHERE a.active = true AND a.niveauUrgence IN :niveaux ORDER BY a.niveauUrgence DESC, a.dateCreation DESC")
    List<Alerte> findActiveAlertesByUrgenceLevel(@Param("niveaux") List<NiveauUrgence> niveaux);
}