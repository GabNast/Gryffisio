package org.generation.italy.model.entities;

import jakarta.persistence.*;


@Entity
@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="patient_code", nullable = false)
    private Integer patientCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="subject_type_id", nullable = false)
    private SubjectType subjectType;

    public Patient(Long id, Integer patientCode, SubjectType subjectType) {
        this.id = id;
        this.patientCode = patientCode;
        this.subjectType = subjectType;
    }

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(Integer patientCode) {
        this.patientCode = patientCode;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }
}
