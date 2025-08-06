package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.AlerteDestinataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlerteDestinataireRepository extends JpaRepository<AlerteDestinataire, Long> {
    List<AlerteDestinataire> findByAlerteId(Long alerteId);
    List<AlerteDestinataire> findByDestinataireId(Long destinataireId);
    List<AlerteDestinataire> findByLu(Boolean lu);
    List<AlerteDestinataire> findByAccuse(Boolean accuse);

    Optional<AlerteDestinataire> findByAlerteIdAndDestinataireId(Long alerteId, Long destinataireId);

    @Query("SELECT ad FROM AlerteDestinataire ad WHERE ad.destinataire.id = :destinataireId AND ad.lu = false")
    List<AlerteDestinataire> findUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT COUNT(ad) FROM AlerteDestinataire ad WHERE ad.destinataire.id = :destinataireId AND ad.lu = false")
    Long countUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT COUNT(ad) FROM AlerteDestinataire ad WHERE ad.alerte.id = :alerteId AND ad.accuse = true")
    Long countAccusedByAlerteId(@Param("alerteId") Long alerteId);
}
