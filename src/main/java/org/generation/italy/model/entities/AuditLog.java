package org.generation.italy.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String action;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "json_details_registration", nullable = false)
    private Registration detailsRegistration;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public AuditLog() {
    }

    public AuditLog(Long id, Registration registration, Admin admin, String action,
                    Registration detailsRegistration, LocalDateTime dateTime) {
        this.id = id;
        this.registration = registration;
        this.admin = admin;
        this.action = action;
        this.detailsRegistration = detailsRegistration;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Registration getDetailsRegistration() {
        return detailsRegistration;
    }

    public void setDetailsRegistration(Registration detailsRegistration) {
        this.detailsRegistration = detailsRegistration;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
