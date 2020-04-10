package com.itmsg.episode.app.asset.resource.ci;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface ItsmCIRepository extends JpaRepository<ItsmCI, Long> {
    ItsmCI findByKaiCiid(String kaiCiid);
}