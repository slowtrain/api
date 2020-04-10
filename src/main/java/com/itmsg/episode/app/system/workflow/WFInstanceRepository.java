package com.itmsg.episode.app.system.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WFInstanceRepository extends JpaRepository<WFInstance, Long> {
    WFInstance findByWfprocessUidAndObjectUidAndStatus(Long wfprocessUid,Long objectUid,String status);
}
