/**
* IT 자원 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface CIRepository extends JpaRepository<CI, Long> {
    CI findByCiId(String ciId);
    CI findByOrgIdAndCiId(String orgId, String ciId);
}
