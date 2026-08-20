package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="subject_type")
public class SubjectType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    public SubjectType(Long id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public SubjectType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
