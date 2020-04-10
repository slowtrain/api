package com.itmsg.episode.app.asset.resource.ci;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface RparespmapRepository extends JpaRepository<Rparespmap, Long> {
    List<Rparespmap> findByCiId(String ciId);
    void deleteByCiId(String ciId);
}