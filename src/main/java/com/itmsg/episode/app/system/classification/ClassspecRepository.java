/**
* 분류 스펙 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassspecRepository extends JpaRepository<Classspec, Long> {
    List<Classspec> findByOrgIdAndClassstructureId(String orgId, String classstructureId);
    void deleteByOrgIdAndClassstructureId(String orgId, String classstructureId);
}
