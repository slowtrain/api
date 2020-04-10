package com.itmsg.episode.app.system.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WFProcessRepository extends JpaRepository<WFProcess, Long> {
    WFProcess findByWfprocessIdAndRevision(String wfprocessId,Integer revision);
    WFProcess findByObjectIdAndRevision(String objectId,Integer revision);
    List<WFProcess> findByObjectIdAndEnabled(String objectId,Boolean enabled);
    List<WFProcess> findByObjectIdAndStatus(String objectId,String status);
}
