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
}
