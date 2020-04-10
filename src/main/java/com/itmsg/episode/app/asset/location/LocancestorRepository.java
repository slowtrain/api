/**
* 위치 조상 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocancestorRepository extends JpaRepository<Locancestor, Long> {   
    
    // void deleteByOrgIdAndAncestorAndClassstructureId(String orgId, String ancestor, String locationId);
    void deleteByOrgIdAndLocationId(String orgId, String locationId);
    List<Locancestor> findByOrgIdAndAncestor(String orgId, String locationId);
}
