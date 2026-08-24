package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "registration_request_evaluation")
public class RegistrationRequestEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_type_id", nullable = false)
    private SessionType sessionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_request_id", nullable = false)
    private RegistrationRequestModification registrationRequest;

    public RegistrationRequestEvaluation() {
    }

    public RegistrationRequestEvaluation(Integer id, Session session, SessionType sessionType, RegistrationRequestModification registrationRequest) {
        this.id = id;
        this.session = session;
        this.sessionType = sessionType;
        this.registrationRequest = registrationRequest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public RegistrationRequestModification getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(RegistrationRequestModification registrationRequest) {
        this.registrationRequest = registrationRequest;
    }
}

