package com.itmsg.episode.app.system.scheduler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
    List<Scheduler> findBySchedulerId(String schedulerId);
}
