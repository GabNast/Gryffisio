package org.generation.italy.model.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "test_to_domain")
public class TestToDomain {

    @EmbeddedId
    private TestToDomainId id = new TestToDomainId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("domainId")
    @JoinColumn(name = "domain_id")
    private Domain domain;
}
