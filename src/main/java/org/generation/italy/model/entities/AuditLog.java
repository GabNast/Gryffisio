package org.generation.italy.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "audit_log_operators",
            joinColumns = @JoinColumn(name = "audit_log_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private Set<Operator> operators = new HashSet<>();

    @Column(nullable = false, length = 50)
    private String action;

    @Column(name = "entity_name", nullable = false, length = 50)
    private String entityName;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public AuditLog() {}

    public AuditLog(Long id, Set<Operator> operators, String action, String entityName, Long entityId, String description, LocalDateTime createdAt) {
        this.id = id;
        this.operators = operators;
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Set<Operator> getOperators() { return operators; }
    public void setOperators(Set<Operator> operators) { this.operators = operators; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}