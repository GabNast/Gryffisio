package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="request_modification")
public class RequestModification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public RequestModification(Long id, Registration registration, Researcher requestingResearcher, String descriptionEdit, char state, LocalDate requestedDate, Admin adminManager) {
        this.id = id;
        this.registration = registration;
        this.requestingResearcher = requestingResearcher;
        this.descriptionEdit = descriptionEdit;
        this.state = state;
        this.requestedDate = requestedDate;
        this.adminManager = adminManager;
    }

    public RequestModification() {
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

    public Researcher getRequestingResearcher() {
        return requestingResearcher;
    }

    public void setRequestingResearcher(Researcher requestingResearcher) {
        this.requestingResearcher = requestingResearcher;
    }

    public String getDescriptionEdit() {
        return descriptionEdit;
    }

    public void setDescriptionEdit(String descriptionEdit) {
        this.descriptionEdit = descriptionEdit;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Admin getAdminManager() {
        return adminManager;
    }

    public void setAdminManager(Admin adminManager) {
        this.adminManager = adminManager;
    }
}
