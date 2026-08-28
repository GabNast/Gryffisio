package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "modification_requests")
public class ModificationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @Column(name = "new_activity_date")
    private LocalDate newActivityDate;

    @Column(name = "new_duration_minutes")
    private Integer newDurationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_session_id")
    private Session newSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_doctor_id")
    private Doctor newDoctor;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handled_by_admin_id")
    private Operator handledByAdmin;

    @Column(name = "handled_at")
    private LocalDateTime handledAt;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    public ModificationRequest() {}

    public ModificationRequest(Long id, Registration registration, Operator operator, LocalDate newActivityDate, Integer newDurationMinutes, Session newSession, Doctor newDoctor, String reason, Status status, LocalDateTime submittedAt, Operator handledByAdmin, LocalDateTime handledAt, String rejectionReason) {
        this.id = id;
        this.registration = registration;
        this.operator = operator;
        this.newActivityDate = newActivityDate;
        this.newDurationMinutes = newDurationMinutes;
        this.newSession = newSession;
        this.newDoctor = newDoctor;
        this.reason = reason;
        this.status = status;
        this.submittedAt = submittedAt;
        this.handledByAdmin = handledByAdmin;
        this.handledAt = handledAt;
        this.rejectionReason = rejectionReason;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Registration getRegistration() { return registration; }
    public void setRegistration(Registration registration) { this.registration = registration; }
    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }
    public LocalDate getNewActivityDate() { return newActivityDate; }
    public void setNewActivityDate(LocalDate newActivityDate) { this.newActivityDate = newActivityDate; }
    public Integer getNewDurationMinutes() { return newDurationMinutes; }
    public void setNewDurationMinutes(Integer newDurationMinutes) { this.newDurationMinutes = newDurationMinutes; }
    public Session getNewSession() { return newSession; }
    public void setNewSession(Session newSession) { this.newSession = newSession; }
    public Doctor getNewDoctor() { return newDoctor; }
    public void setNewDoctor(Doctor newDoctor) { this.newDoctor = newDoctor; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public Operator getHandledByAdmin() { return handledByAdmin; }
    public void setHandledByAdmin(Operator handledByAdmin) { this.handledByAdmin = handledByAdmin; }
    public LocalDateTime getHandledAt() { return handledAt; }
    public void setHandledAt(LocalDateTime handledAt) { this.handledAt = handledAt; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}