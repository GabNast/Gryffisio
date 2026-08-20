package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="test")

public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, length = 128)
    private String name;

    @ManyToMany
    @JoinTable(
            name="test_to_domain",
            joinColumns = @JoinColumn(name = "domain_id"),
            inverseJoinColumns = @JoinColumn(name="test_id")
    )
    private Set<Domain> domains = new HashSet<>();

}
