package com.itmsg.episode.app.system.assignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAssignmentId(String assignmentId);
}
