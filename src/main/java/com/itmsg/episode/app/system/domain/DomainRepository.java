package com.itmsg.episode.app.system.domain;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    List<Domain> findByDomainId(String domainId,Sort sort);
}
