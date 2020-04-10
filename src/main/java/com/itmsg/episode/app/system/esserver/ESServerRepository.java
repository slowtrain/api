package com.itmsg.episode.app.system.esserver;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ESServerRepository extends JpaRepository<ESServer, Long> {
    List<ESServer> findByIpAddress(String ipAddress);
    ESServer findBySeq(Integer seq);
}
