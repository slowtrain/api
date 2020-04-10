/**
* 어플리케이션 모듈 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppModuleRepository extends JpaRepository<AppModule, Long> {
    List<AppModule> findByAppId(String appId);   
}
