package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="referring_doctor")
public class ReferringDoctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String surname;

    @Column(nullable = false)
    private char gender;
}
