package com.sante.reventionplatform.service;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository1 userRepository1;

    @Autowired
    private TarifRepository tarifRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket getTicketWithDetails(Long id) {
        return ticketRepository.findByIdWithDetails(id);
    }

    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public Ticket getTicketByNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber);
    }

    public Ticket createTicket(Long userId, Long tarifId, LocalDateTime appointmentDate) {
        Optional<User1> userOpt = userRepository1.findById(userId);
        Optional<Tarif> tarifOpt = tarifRepository.findById(tarifId);

        if (userOpt.isEmpty() || tarifOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur ou tarif introuvable");
        }

        User1 user = userOpt.get();
        Tarif tarif = tarifOpt.get();

        String ticketNumber = generateTicketNumber();
        Ticket ticket = new Ticket(ticketNumber, user, tarif, appointmentDate);

        return ticketRepository.save(ticket);
    }

    public Ticket updateTicketStatus(Long ticketId, Ticket.TicketStatus status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new RuntimeException("Ticket introuvable");
        }

        Ticket ticket = ticketOpt.get();
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    private String generateTicketNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "TK" + timestamp + String.valueOf((int)(Math.random() * 1000));
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}