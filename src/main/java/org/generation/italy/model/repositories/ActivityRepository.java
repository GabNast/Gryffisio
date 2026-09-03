package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByParentIsNull();
    List<Activity> findByParent_Id(Integer parentId);
}