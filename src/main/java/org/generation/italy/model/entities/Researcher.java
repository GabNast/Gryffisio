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

    public Researcher(Long id, String name, String surname, boolean isStudent, boolean isActive) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.isStudent = isStudent;
        this.isActive = isActive;
    }

    public Researcher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
