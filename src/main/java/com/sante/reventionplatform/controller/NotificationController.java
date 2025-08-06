package com.sante.reventionplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.sante.reventionplatform.entity.*;

import com.sante.reventionplatform.repository.*;
import com.sante.reventionplatform.service.*;
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        return notification.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!notificationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destinataire/{destinataireId}")
    public ResponseEntity<List<Notification>> getNotificationsByDestinataire(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(notificationService.findByDestinataireId(destinataireId));
    }

    @GetMapping("/destinataire/{destinataireId}/non-lues")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(notificationService.findUnreadNotifications(destinataireId));
    }

    @GetMapping("/destinataire/{destinataireId}/count-non-lues")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(destinataireId));
    }

    @PutMapping("/{id}/marquer-lu")
    public ResponseEntity<Void> marquerCommeLu(@PathVariable Long id) {
        if (!notificationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        notificationService.marquerCommeLu(id);
        return ResponseEntity.ok().build();
    }
}
