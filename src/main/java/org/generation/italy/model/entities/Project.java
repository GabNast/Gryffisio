package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 5)
    private String acronym;
}
