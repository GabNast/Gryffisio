package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "registration_subject")
public class RegistrationSubject {
    @EmbeddedId
    private RegistrationSubjectId id = new RegistrationSubjectId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("registrationId")
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("patientId")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public RegistrationSubject() {
    }


    public RegistrationSubject(RegistrationSubjectId id, Registration registration, Patient patient) {
        this.id = id;
        this.registration = registration;
        this.patient = patient;
    }

    public RegistrationSubjectId getId() {
        return id;
    }

    public void setId(RegistrationSubjectId id) {
        this.id = id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
