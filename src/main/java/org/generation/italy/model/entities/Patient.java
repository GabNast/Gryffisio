package org.generation.italy.model.entities;

import jakarta.persistence.*;


@Entity
@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="patient_code", nullable = false)
    private Integer patientCode;

    @ManyToOne
    @JoinColumn(name="project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name="subject_type_id", nullable = false)
    private SubjectType subjectType;
}
