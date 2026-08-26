package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RegistrationSubjectId implements Serializable {

    @Column(name = "registration_id")
    private Long registrationId;

    @Column(name = "patient_id")
    private Long patientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationSubjectId that)) return false;
        return Objects.equals(registrationId, that.registrationId)
                && Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationId, patientId);
    }
}
