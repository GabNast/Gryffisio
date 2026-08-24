package org.generation.italy.model.entities;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class OperatorRegistrationId implements Serializable {
    @Column(name = "registration_id")
    private Long registrationId;

    @Column(name = "researcher_id")
    private Long researcherId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperatorRegistrationId that)) return false;
        return Objects.equals(registrationId, that.registrationId)
                && Objects.equals(researcherId, that.researcherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationId, researcherId);
    }
}
