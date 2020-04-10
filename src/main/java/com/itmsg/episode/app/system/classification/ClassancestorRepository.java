/**
* 분류 조상 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ClassancestorRepository extends JpaRepository<Classancestor, Long> {   
    
    @Modifying
    @Transactional
    void deleteByOrgIdAndAncestorAndClassstructureId(String orgId, String ancestor, String classstructureId);
    void deleteByOrgIdAndClassstructureId(String orgId, String classstructureId);
    List<Classancestor> findByOrgIdAndAncestor(String orgId, String classstructureId);
}
