package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="domain")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;
}
