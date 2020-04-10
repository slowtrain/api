/**
* 유저 패스워드 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {
    UserPassword findTop1ByUsernameOrderByCreateDtDesc(String username);
    List<UserPassword> findTop3ByUsernameOrderByCreateDtDesc(String username);
}