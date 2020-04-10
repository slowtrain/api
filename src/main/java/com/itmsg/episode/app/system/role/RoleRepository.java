/**
* 롤 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById (String id);
    List<Role> findByRoleId(String roleId);
}