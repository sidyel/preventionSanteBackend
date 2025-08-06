package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageDestinataireRepository messageDestinataireRepository;
    private final NotificationService notificationService;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Message update(Message message) {
        return messageRepository.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> findByExpediteurId(Long expediteurId) {
        return messageRepository.findByExpediteurIdOrderByDateCreationDesc(expediteurId);
    }

    public List<Message> findByDestinataireId(Long destinataireId) {
        return messageRepository.findByDestinataireId(destinataireId);
    }

    public List<Message> findUnreadMessagesByDestinataireId(Long destinataireId) {
        return messageRepository.findUnreadMessagesByDestinataireId(destinataireId);
    }

    public List<Message> findByCampagneId(Long campagneId) {
        return messageRepository.findByCampagneId(campagneId);
    }

    public List<Message> findByTypeMessage(TypeMessage typeMessage) {
        return messageRepository.findByTypeMessage(typeMessage);
    }

    public List<Message> findByPriorite(PrioriteMessage priorite) {
        return messageRepository.findByPriorite(priorite);
    }

    public Message envoyerMessage(Message message, List<User> destinataires) {
        message.setDateEnvoi(LocalDateTime.now());
        message.setEnvoye(true);
        Message messageSauve = messageRepository.save(message);

        // Créer les relations message-destinataires
        for (User destinataire : destinataires) {
            MessageDestinataire md = new MessageDestinataire();
            md.setMessage(messageSauve);
            md.setDestinataire(destinataire);
            md.setDateReception(LocalDateTime.now());
            messageDestinataireRepository.save(md);

            // Créer une notification
            notificationService.creerNotificationMessage(destinataire, messageSauve);
        }

        return messageSauve;
    }

    public void marquerCommeLu(Long messageId, Long destinataireId) {
        Optional<MessageDestinataire> md = messageDestinataireRepository
                .findByMessageIdAndDestinataireId(messageId, destinataireId);
        if (md.isPresent() && !md.get().getLu()) {
            md.get().setLu(true);
            md.get().setDateLecture(LocalDateTime.now());
            messageDestinataireRepository.save(md.get());
        }
    }

    public Long countUnreadMessages(Long destinataireId) {
        return messageDestinataireRepository.countUnreadByDestinataireId(destinataireId);
    }

    public Long countSentMessages(Long expediteurId) {
        return messageRepository.countSentMessagesByExpediteur(expediteurId);
    }
}
