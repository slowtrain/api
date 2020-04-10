package com.itmsg.episode.app.asset.resource.ci;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface CIspecRepository extends JpaRepository<CIspec, Long> {
    List<CIspec> findByOrgIdAndCiId(String orgId, String ciId);
    void deleteByCiId(String ciId);
}