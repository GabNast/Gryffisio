package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(nullable = false, length = 255)
    private String action;

    @ManyToOne
    @JoinColumn(name = "json_details_registration")
    private Registration jsonRegistration;

    @Column(name="date_time", nullable = false)
    private LocalDateTime dateTime;

    public AuditLog(Long id, Registration registration, Admin admin, String action, Registration jsonRegistration, LocalDateTime dateTime) {
        this.id = id;
        this.registration = registration;
        this.admin = admin;
        this.action = action;
        this.jsonRegistration = jsonRegistration;
        this.dateTime = dateTime;
    }

    public AuditLog() {
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

    public Registration getJsonRegistration() {
        return jsonRegistration;
    }

    public void setJsonRegistration(Registration jsonRegistration) {
        this.jsonRegistration = jsonRegistration;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
