/**
* IT 자원 변경 이력 Jparepository 클래스
* @author   최영훈
* @since    2019.11.18
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cihis;

import com.itmsg.episode.app.asset.resource.cihis.CIhis;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CIhisRepository extends JpaRepository<CIhis, Long> {}