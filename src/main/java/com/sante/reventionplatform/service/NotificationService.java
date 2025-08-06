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
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> findByDestinataireId(Long destinataireId) {
        return notificationRepository.findByDestinataireIdOrderByDateCreationDesc(destinataireId);
    }

    public List<Notification> findUnreadNotifications(Long destinataireId) {
        return notificationRepository.findUnreadByDestinataireId(destinataireId);
    }

    public Long countUnreadNotifications(Long destinataireId) {
        return notificationRepository.countUnreadByDestinataireId(destinataireId);
    }

    public void marquerCommeLu(Long notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isPresent() && !notification.get().getLu()) {
            notification.get().setLu(true);
            notification.get().setDateLecture(LocalDateTime.now());
            notificationRepository.save(notification.get());
        }
    }

    public void creerNotificationMessage(User destinataire, Message message) {
        Notification notification = new Notification();
        notification.setTitre("Nouveau message reçu");
        notification.setContenu("Vous avez reçu un nouveau message: " + message.getTitre());
        notification.setTypeNotification(TypeNotification.NOUVEAU_MESSAGE);
        notification.setDestinataire(destinataire);
        notification.setUrlAction("/messages/" + message.getId());
        notificationRepository.save(notification);
    }

    public void creerNotificationAlerte(User destinataire, Alerte alerte) {
        Notification notification = new Notification();
        notification.setTitre("Nouvelle alerte");
        notification.setContenu("Alerte " + alerte.getNiveauUrgence() + ": " + alerte.getTitre());
        notification.setTypeNotification(TypeNotification.NOUVELLE_ALERTE);
        notification.setDestinataire(destinataire);
        notification.setUrlAction("/alertes/" + alerte.getId());
        notificationRepository.save(notification);
    }

    public void creerNotificationCampagne(User destinataire, Campagne campagne, TypeNotification type) {
        Notification notification = new Notification();
        if (type == TypeNotification.CAMPAGNE_DEMARREE) {
            notification.setTitre("Campagne démarrée");
            notification.setContenu("La campagne '" + campagne.getNom() + "' a démarré");
        } else if (type == TypeNotification.CAMPAGNE_TERMINEE) {
            notification.setTitre("Campagne terminée");
            notification.setContenu("La campagne '" + campagne.getNom() + "' est terminée");
        }
        notification.setTypeNotification(type);
        notification.setDestinataire(destinataire);
        notification.setUrlAction("/campagnes/" + campagne.getId());
        notificationRepository.save(notification);
    }

    public void creerNotificationRappel(User destinataire, String titre, String contenu) {
        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setContenu(contenu);
        notification.setTypeNotification(TypeNotification.RAPPEL);
        notification.setDestinataire(destinataire);
        notificationRepository.save(notification);
    }
}