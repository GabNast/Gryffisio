package org.generation.italy.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "researcher")
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(name = "is_student", nullable = false)
    private Boolean student = false;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    public Researcher() {
    }

    public Researcher(Long id, String name, String surname, Boolean student, Boolean active) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.student = student;
        this.active = active;
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

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {
        this.student = student;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
