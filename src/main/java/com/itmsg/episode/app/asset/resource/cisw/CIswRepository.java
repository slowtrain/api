/**
* 소프트웨어 목록 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.08.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cisw;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CIswRepository extends JpaRepository<CIsw, Long> {
    CIsw findById(String id);
}