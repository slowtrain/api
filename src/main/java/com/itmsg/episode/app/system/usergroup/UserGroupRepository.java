/**
* 유저 그룹 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findById (String id);
    List<UserGroup> findByUsergroupId(String usergroup_id);
    UserGroup findByUsergroupIdAndOrgId(String usergroup_id,String orgId);
}