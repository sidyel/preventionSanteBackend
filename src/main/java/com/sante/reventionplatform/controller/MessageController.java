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
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.findById(id);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message) {
        if (!messageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        message.setId(id);
        Message updatedMessage = messageService.update(message);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (!messageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expediteur/{expediteurId}")
    public ResponseEntity<List<Message>> getMessagesByExpediteur(@PathVariable Long expediteurId) {
        return ResponseEntity.ok(messageService.findByExpediteurId(expediteurId));
    }

    @GetMapping("/destinataire/{destinataireId}")
    public ResponseEntity<List<Message>> getMessagesByDestinataire(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(messageService.findByDestinataireId(destinataireId));
    }

    @GetMapping("/destinataire/{destinataireId}/non-lus")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(messageService.findUnreadMessagesByDestinataireId(destinataireId));
    }

    @GetMapping("/campagne/{campagneId}")
    public ResponseEntity<List<Message>> getMessagesByCampagne(@PathVariable Long campagneId) {
        return ResponseEntity.ok(messageService.findByCampagneId(campagneId));
    }

    @GetMapping("/type/{typeMessage}")
    public ResponseEntity<List<Message>> getMessagesByType(@PathVariable TypeMessage typeMessage) {
        return ResponseEntity.ok(messageService.findByTypeMessage(typeMessage));
    }

    @GetMapping("/priorite/{priorite}")
    public ResponseEntity<List<Message>> getMessagesByPriorite(@PathVariable PrioriteMessage priorite) {
        return ResponseEntity.ok(messageService.findByPriorite(priorite));
    }

    @PostMapping("/{id}/envoyer")
    public ResponseEntity<Message> envoyerMessage(@PathVariable Long id, @RequestBody List<Long> destinataireIds) {
        Optional<Message> messageOpt = messageService.findById(id);
        if (!messageOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<User> destinataires = destinataireIds.stream()
                .map(userService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        Message messageSent = messageService.envoyerMessage(messageOpt.get(), destinataires);
        return ResponseEntity.ok(messageSent);
    }

    @PutMapping("/{messageId}/marquer-lu/{destinataireId}")
    public ResponseEntity<Void> marquerCommeLu(@PathVariable Long messageId, @PathVariable Long destinataireId) {
        messageService.marquerCommeLu(messageId, destinataireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/destinataire/{destinataireId}/count-non-lus")
    public ResponseEntity<Long> countUnreadMessages(@PathVariable Long destinataireId) {
        return ResponseEntity.ok(messageService.countUnreadMessages(destinataireId));
    }
}
