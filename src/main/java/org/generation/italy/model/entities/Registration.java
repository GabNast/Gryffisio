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
}
