package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domains")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "domain_activities",
            joinColumns = @JoinColumn(name = "domain_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<Activity> activities = new HashSet<>();

    public Domain() {}

    public Domain(Integer id, String name, Set<Activity> activities) {
        this.id = id;
        this.name = name;
        this.activities = activities;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Activity> getActivities() { return activities; }
    public void setActivities(Set<Activity> activities) { this.activities = activities; }
}
