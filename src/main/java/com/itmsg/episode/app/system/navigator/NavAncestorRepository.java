/**
* 메뉴 조상 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.navigator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NavAncestorRepository extends JpaRepository<NavAncestor, Long> {   
    
    void deleteByOrgIdAndAncestorAndNavigatorId(String orgId, String ancestor, String navigatorId);
    void deleteByOrgIdAndNavigatorId(String orgId, String navigatorId);
    List<NavAncestor> findByOrgIdAndAncestor(String orgId, String navigatorId);
}
