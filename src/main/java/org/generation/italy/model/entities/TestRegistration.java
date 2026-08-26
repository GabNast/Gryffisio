package org.generation.italy.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_registration")
public class TestRegistration {

    @EmbeddedId
    private TestRegistrationId id = new TestRegistrationId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("registrationId")
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    public TestRegistration() {
    }

    public TestRegistration(TestRegistrationId id, Registration registration, Test test) {
        this.id = id;
        this.registration = registration;
        this.test = test;
    }

    public TestRegistrationId getId() {
        return id;
    }

    public void setId(TestRegistrationId id) {
        this.id = id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
