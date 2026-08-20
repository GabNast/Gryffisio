package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="subject_type")
public class SubjectType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}
