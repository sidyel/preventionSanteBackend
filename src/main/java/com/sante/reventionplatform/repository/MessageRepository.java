package com.sante.reventionplatform.repository;


import com.sante.reventionplatform.entity.Message;
import com.sante.reventionplatform.entity.PrioriteMessage;
import com.sante.reventionplatform.entity.TypeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByExpediteurId(Long expediteurId);
    List<Message> findByCampagneId(Long campagneId);
    List<Message> findByTypeMessage(TypeMessage typeMessage);
    List<Message> findByPriorite(PrioriteMessage priorite);
    List<Message> findByEnvoye(Boolean envoye);

    @Query("SELECT m FROM Message m WHERE m.expediteur.id = :expediteurId ORDER BY m.dateCreation DESC")
    List<Message> findByExpediteurIdOrderByDateCreationDesc(@Param("expediteurId") Long expediteurId);

    @Query("SELECT m FROM Message m JOIN m.destinataires md WHERE md.destinataire.id = :destinataireId")
    List<Message> findByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT m FROM Message m JOIN m.destinataires md WHERE md.destinataire.id = :destinataireId AND md.lu = false")
    List<Message> findUnreadMessagesByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT m FROM Message m WHERE m.dateEnvoi BETWEEN :dateDebut AND :dateFin")
    List<Message> findByDateEnvoiBetween(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.expediteur.id = :expediteurId AND m.envoye = true")
    Long countSentMessagesByExpediteur(@Param("expediteurId") Long expediteurId);
}
