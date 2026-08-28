package org.generation.italy.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_type_id", nullable = false)
    private SubjectType subjectType;

    public Subject() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public SubjectType getSubjectType() { return subjectType; }
    public void setSubjectType(SubjectType subjectType) { this.subjectType = subjectType; }
}