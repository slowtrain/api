/**
* 유저 로그 엔터티 JapaRepository 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserlogRepository extends JpaRepository<Userlog, Long> {}