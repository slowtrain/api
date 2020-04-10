/**
* 롤 권한 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleAuthRepository extends JpaRepository<RoleAuth, Long> {
    int deleteByRoleId(String roleId);
    RoleAuth findById(String id);
    RoleAuth findByRoleIdAndAppIdAndAuthorityId(String roldId, String appId, String authorityId);
    List<RoleAuth> findByRoleId(String roldId);
}