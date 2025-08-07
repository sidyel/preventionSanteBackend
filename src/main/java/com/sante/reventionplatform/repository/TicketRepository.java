package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.*;
import com.sante.reventionplatform.repository.*;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByStatus(Ticket.TicketStatus status);
    Ticket findByTicketNumber(String ticketNumber);

    @Query("SELECT t FROM Ticket t WHERE t.appointmentDate BETWEEN :start AND :end")
    List<Ticket> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.tarif LEFT JOIN FETCH t.user WHERE t.id = :id")
    Ticket findByIdWithDetails(Long id);
}