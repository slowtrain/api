/**
* 롤 필터 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleFilterRepository extends JpaRepository<RoleFilter, Long> {
    int deleteByRoleId(String roleId);
    RoleFilter findById(String id);
    RoleFilter findByRoleIdAndDatafilterId(String roleId, String datafilterId);
}