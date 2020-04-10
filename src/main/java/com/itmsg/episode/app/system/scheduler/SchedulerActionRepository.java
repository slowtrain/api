package com.itmsg.episode.app.system.scheduler;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerActionRepository extends JpaRepository<SchedulerAction, Long> {
    List<SchedulerAction> findBySchedulerId(String schedulerId, Sort sort);
}
