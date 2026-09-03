package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Activity parent;

    public Activity() {}

    public Activity(Integer id, String name, Activity parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Activity getParent() { return parent; }
    public void setParent(Activity parent) { this.parent = parent; }
}