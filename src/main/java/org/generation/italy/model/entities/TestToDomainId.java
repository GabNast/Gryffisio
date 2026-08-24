package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class TestToDomainId implements Serializable {
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "domain_id")
    private Long domainId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestToDomainId that)) return false;
        return Objects.equals(testId, that.testId)
                && Objects.equals(domainId, that.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId, domainId);
    }
}
