package com.itmsg.episode.app.asset.resource.disci;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisCIspecRepository extends JpaRepository<DisCIspec, Long> {
    List<DisCIspec> findByDisciId(String orgId);
}