package com.itmsg.episode.app.system.scheduler;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerHistoryRepository extends JpaRepository<SchedulerHistory, Long> {
    List<SchedulerHistory> findBySchedulerId(String schedulerId,Sort sort);
    
}
