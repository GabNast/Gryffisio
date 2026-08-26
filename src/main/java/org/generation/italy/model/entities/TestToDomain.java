package org.generation.italy.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

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

    public TestToDomain() {
    }

    public TestToDomain(TestToDomainId id, Test test, Domain domain) {
        this.id = id;
        this.test = test;
        this.domain = domain;
    }

    public TestToDomainId getId() {
        return id;
    }

    public void setId(TestToDomainId id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
