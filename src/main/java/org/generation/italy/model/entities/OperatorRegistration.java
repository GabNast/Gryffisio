package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "operator_registration")
public class OperatorRegistration {
    @EmbeddedId
    private OperatorRegistrationId id = new OperatorRegistrationId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("registrationId")
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("researcherId")
    @JoinColumn(name = "researcher_id")
    private Researcher researcher;

    public OperatorRegistration() {
    }

    public OperatorRegistration(OperatorRegistrationId id, Registration registration, Researcher researcher) {
        this.id = id;
        this.registration = registration;
        this.researcher = researcher;
    }

    public OperatorRegistrationId getId() {
        return id;
    }

    public void setId(OperatorRegistrationId id) {
        this.id = id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }
}
