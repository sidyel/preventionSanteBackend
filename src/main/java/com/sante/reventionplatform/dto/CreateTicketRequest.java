package com.sante.reventionplatform.dto;

import java.time.LocalDateTime;

public class CreateTicketRequest {
    private Long userId;
    private Long tarifId;
    private LocalDateTime appointmentDate;

    public CreateTicketRequest() {}

    public CreateTicketRequest(Long userId, Long tarifId, LocalDateTime appointmentDate) {
        this.userId = userId;
        this.tarifId = tarifId;
        this.appointmentDate = appointmentDate;
    }

    // Getters et Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTarifId() { return tarifId; }
    public void setTarifId(Long tarifId) { this.tarifId = tarifId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
}