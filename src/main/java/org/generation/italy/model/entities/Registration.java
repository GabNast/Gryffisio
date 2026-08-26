package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="registration")
public class Registration {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    @Column(name="date",nullable = true)
    private LocalDate date;

    @Column(name="duration_minutes", nullable = false)
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referring_doctor_id")
    private ReferringDoctor referringDoctor;

    @Column(name="creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name="is_modified", nullable = false)
    private Boolean isModified = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToMany
    @JoinTable(
            name="operator_registration",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name="researcher_id")
    )
    private Set<Researcher> researchers = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name="registration_subject",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name="patient_id")
    )
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="test_registration",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name="test_id")
    )
    private Set<Test> tests = new HashSet<>();

    public Registration(Long id, Domain domain, Project project, ReferringDoctor referringDoctor, LocalDate date, Integer durationMinutes, LocalDateTime creationDate, LocalDateTime lastModifiedDate, Boolean isModified, Set<Researcher> researchers, Set<Patient> patients, Set<Test> tests, Patient patient) {
        this.id = id;
        this.domain = domain;
        this.project = project;
        this.referringDoctor = referringDoctor;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.isModified = isModified;
        this.researchers = researchers;
        this.patients = patients;
        this.tests = tests;
        this.patient = patient;
    }

    public Registration() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ReferringDoctor getReferringDoctor() {
        return referringDoctor;
    }

    public void setReferringDoctor(ReferringDoctor referringDoctor) {
        this.referringDoctor = referringDoctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getModified() {
        return isModified;
    }

    public void setModified(Boolean modified) {
        isModified = modified;
    }

    public Set<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(Set<Researcher> researchers) {
        this.researchers = researchers;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
