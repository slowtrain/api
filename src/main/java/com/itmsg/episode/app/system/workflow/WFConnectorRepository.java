package com.itmsg.episode.app.system.workflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WFConnectorRepository extends JpaRepository<WFConnector, Long> {
    List<WFConnector> findByWfprocessUid(Long wfprocessUid);
    WFConnector findByWfprocessUidAndWfconnectorId(Long wfprocessUid,String wfconnectorId);
    List<WFConnector> findBySourceID(String sourceID);
    List<WFConnector> findByWfprocessUidAndSourceID(Long wfprocessUid,String sourceID);
}
