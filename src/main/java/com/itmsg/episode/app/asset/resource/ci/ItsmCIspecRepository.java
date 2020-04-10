package com.itmsg.episode.app.asset.resource.ci;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface ItsmCIspecRepository extends JpaRepository<ItsmCIspec, Long> {
    List<ItsmCIspec> findByCiId(String ciId);
}