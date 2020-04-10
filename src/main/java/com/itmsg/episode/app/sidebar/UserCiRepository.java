/**
* 관심장비 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.08.13
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.13   이정호   최초 생성
*/
package com.itmsg.episode.app.sidebar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCiRepository extends JpaRepository<UserCi, Long> {
    UserCi findById (String id);
    void deleteByCiId (String ciId);
}