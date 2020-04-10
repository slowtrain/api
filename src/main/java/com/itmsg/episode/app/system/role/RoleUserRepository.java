/**
* 롤 유저 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    int deleteByRoleId(String roleId);
    RoleUser findById(String id);
    RoleUser findByRoleIdAndUsername(String roleId, String username);
    List<RoleUser> getByRoleId(String roleId);
    void deleteByUsername (String username);
}