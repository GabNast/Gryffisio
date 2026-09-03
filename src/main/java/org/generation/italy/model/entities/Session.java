package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "session", nullable = false, unique = true, length = 50)
    private String session;

    public Session() {}

    public Session(Integer id, String session) {
        this.id = id;
        this.session = session;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }
}