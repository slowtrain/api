/**
* 분류 사용 오브젝트 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassusewithRepository extends JpaRepository<Classusewith, Long> {
    List<Classusewith> findByOrgIdAndClassstructureId(String orgId, String classstructureId);
    List<Classusewith> findByObject(String object);
}
