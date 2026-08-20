package org.generation.italy.model.entities;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="registration")
public class Registration {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "referring_doctor_id")
    private ReferringDoctor referringDoctor;

    @ManyToOne
    @JoinColumn(name = "subject_type_id")
    private SubjectType subjectType;

    @Column(name="date",nullable = false)
    private LocalDate date;

    @Column(name="duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String session;

    @Column(name="creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name="last_modified_date", nullable = false)
    private LocalDate lastModifiedDate;

    @Column(name="is_modified", nullable = false)
    private Boolean isModified = false;

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

    public Registration(Long id, Domain domain, Project project, ReferringDoctor referringDoctor, SubjectType subjectType, LocalDate date, Integer durationMinutes, String session, LocalDate creationDate, LocalDate lastModifiedDate, Boolean isModified, Set<Researcher> researchers, Set<Patient> patients, Set<Test> tests) {
        this.id = id;
        this.domain = domain;
        this.project = project;
        this.referringDoctor = referringDoctor;
        this.subjectType = subjectType;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.session = session;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.isModified = isModified;
        this.researchers = researchers;
        this.patients = patients;
        this.tests = tests;
    }

    public Registration() {
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

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
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
