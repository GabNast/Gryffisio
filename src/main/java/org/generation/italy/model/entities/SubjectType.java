package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subject_types")
public class SubjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String type;

    public SubjectType() {}

    public SubjectType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}