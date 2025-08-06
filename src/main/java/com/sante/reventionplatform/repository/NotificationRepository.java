package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.Notification;
import com.sante.reventionplatform.entity.TypeNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDestinataireId(Long destinataireId);
    List<Notification> findByTypeNotification(TypeNotification typeNotification);
    List<Notification> findByLu(Boolean lu);

    @Query("SELECT n FROM Notification n WHERE n.destinataire.id = :destinataireId ORDER BY n.dateCreation DESC")
    List<Notification> findByDestinataireIdOrderByDateCreationDesc(@Param("destinataireId") Long destinataireId);

    @Query("SELECT n FROM Notification n WHERE n.destinataire.id = :destinataireId AND n.lu = false")
    List<Notification> findUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.destinataire.id = :destinataireId AND n.lu = false")
    Long countUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);
}
