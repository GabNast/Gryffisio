package org.generation.italy.model.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "session_type")
public class SessionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_type_category_id", nullable = false)
    private SessionTypeCategory sessionTypeCategory;

    public SessionType() {
    }

    public SessionType(Integer id, String name, String code, SessionTypeCategory sessionTypeCategory) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sessionTypeCategory = sessionTypeCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SessionTypeCategory getSessionTypeCategory() {
        return sessionTypeCategory;
    }

    public void setSessionTypeCategory(SessionTypeCategory sessionTypeCategory) {
        this.sessionTypeCategory = sessionTypeCategory;
    }
}

