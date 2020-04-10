package com.itmsg.episode.app.system.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WFCallstackRepository extends JpaRepository<WFCallstack, Long> {
    WFCallstack findFirstByWfinstanceUidOrderByIdDesc(Long wfinstanceUid);
}
