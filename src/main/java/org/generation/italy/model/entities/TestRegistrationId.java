package org.generation.italy.model.entities;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class TestRegistrationId implements Serializable{
    @Column(name = "registration_id")
    private Long registrationId;

    @Column(name = "test_id")
    private Long testId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestRegistrationId that)) return false;
        return Objects.equals(registrationId, that.registrationId)
                && Objects.equals(testId, that.testId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationId, testId);
    }
}
