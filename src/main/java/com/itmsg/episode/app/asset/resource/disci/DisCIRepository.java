package com.itmsg.episode.app.asset.resource.disci;

import java.util.Stack;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisCIRepository extends JpaRepository<DisCI, Long> {
    Stack<DisCI> findByDatasrcAndIsupdate(String datasrc,Boolean isupdate,Sort sort);
}