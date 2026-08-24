package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "registration_request_modification")
public class RegistrationRequestModification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requesting_researcher_id", nullable = false)
    private Researcher requestingResearcher;

    // "char" NOT NULL -> probabile stato tipo 'P'/'A'/'R'.
    // Valuta di sostituirlo con un enum + @Convert se i valori ammessi sono fissi.
    @Column(name = "state", nullable = false)
    private Character state;

    @Column(name = "requested_date", nullable = false)
    private LocalDate requestedDate;

    // nullable: valorizzato quando un admin gestisce la richiesta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_manager")
    private Admin adminManager;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "duration")
    private Integer duration;

    // nullable: la richiesta puo' non essere ancora legata a una registration esistente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private Registration registration;

    public RegistrationRequestModification() {
    }

    public RegistrationRequestModification(Long id, Researcher requestingResearcher, Character state, LocalDate requestedDate, Admin adminManager, LocalDate date, Integer duration, Registration registration) {
        this.id = id;
        this.requestingResearcher = requestingResearcher;
        this.state = state;
        this.requestedDate = requestedDate;
        this.adminManager = adminManager;
        this.date = date;
        this.duration = duration;
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Researcher getRequestingResearcher() {
        return requestingResearcher;
    }

    public void setRequestingResearcher(Researcher requestingResearcher) {
        this.requestingResearcher = requestingResearcher;
    }

    public Character getState() {
        return state;
    }

    public void setState(Character state) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
