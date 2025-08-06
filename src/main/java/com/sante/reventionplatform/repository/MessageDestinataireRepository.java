package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.MessageDestinataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageDestinataireRepository extends JpaRepository<MessageDestinataire, Long> {
    List<MessageDestinataire> findByMessageId(Long messageId);
    List<MessageDestinataire> findByDestinataireId(Long destinataireId);
    List<MessageDestinataire> findByLu(Boolean lu);

    Optional<MessageDestinataire> findByMessageIdAndDestinataireId(Long messageId, Long destinataireId);

    @Query("SELECT md FROM MessageDestinataire md WHERE md.destinataire.id = :destinataireId AND md.lu = false")
    List<MessageDestinataire> findUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT COUNT(md) FROM MessageDestinataire md WHERE md.destinataire.id = :destinataireId AND md.lu = false")
    Long countUnreadByDestinataireId(@Param("destinataireId") Long destinataireId);

    @Query("SELECT COUNT(md) FROM MessageDestinataire md WHERE md.message.id = :messageId AND md.lu = true")
    Long countReadByMessageId(@Param("messageId") Long messageId);
}
