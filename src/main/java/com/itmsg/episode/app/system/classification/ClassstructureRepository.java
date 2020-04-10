/**
* 분류 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassstructureRepository extends JpaRepository<Classstructure, Long> {
    Classstructure findById(long id);
}
