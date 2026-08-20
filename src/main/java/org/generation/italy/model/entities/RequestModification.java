package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="request_modification")
public class RequestModification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="registration_id",nullable = false)
    private Registration registration;

    @ManyToOne
    @JoinColumn(name="requesting_researcher_id",nullable = false)
    private Researcher requestingResearcher;

    @Column(name="description_edit", nullable = false, columnDefinition = "TEXT")
    private String descriptionEdit;

    @Column(nullable = false)
    private char state;

    @Column(name="requested_date", nullable = false)
    private LocalDate requestedDate;

    @ManyToOne
    @JoinColumn(name="admin_manager",nullable = false)
    private Admin adminManager;
}
