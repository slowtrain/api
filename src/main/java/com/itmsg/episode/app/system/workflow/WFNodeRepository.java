package com.itmsg.episode.app.system.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WFNodeRepository extends JpaRepository<WFNode, Long> {
    List<WFNode> findByWfprocessUid(Long wfprocessUid);
    WFNode findByWfprocessUidAndWfnodeId(Long wfprocessUid,String wfnodeId);
}
