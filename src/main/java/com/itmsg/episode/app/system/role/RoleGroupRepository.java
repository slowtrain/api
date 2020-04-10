/**
* 롤 그룹 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {
    List<RoleGroup> findByRoleId(String roleId);
    RoleGroup findByRoleIdAndUsergroupId(String roleId, String usergroupId);

    void deleteByUsergroupId (String usergroupId);

}