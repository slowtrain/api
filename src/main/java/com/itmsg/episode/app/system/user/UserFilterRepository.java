/**
* 유저 필터 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFilterRepository extends JpaRepository<UserFilter, Long> {
    List<UserFilter> findByUsername(String username);
    Optional<UserFilter> findByUsernameAndDatafilterId(String username,String datafilterId);
    int deleteByUsernameAndDatafilterId(String username, String datafilterId);
}