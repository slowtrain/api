/**
* 소프트웨어 카탈로그 엔터티 Jparepository 클래스
* @author   최영훈
* @since    2019.10.14
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.14   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.swcat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SwcatRepository extends JpaRepository<Swcat, Long> {}