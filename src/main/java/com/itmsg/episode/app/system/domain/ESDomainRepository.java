/**
* 코드체계 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ESDomainRepository extends JpaRepository<ESDomain, Long> {
    List<ESDomain> findById(String id);
}
