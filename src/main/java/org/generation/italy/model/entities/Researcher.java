package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="researcher")
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(name="is_student", nullable = false)
    private boolean isStudent=false;

    @Column(name="is_active", nullable = false)
    private boolean isActive=true;
}
