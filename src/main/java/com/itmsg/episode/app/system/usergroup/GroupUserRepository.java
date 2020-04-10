/**
* 그룹 유저 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    int deleteByUsergroupId(String id);
    GroupUser findById(String id);
    GroupUser findByUsergroupIdAndUsername(String groupuser_id, String username);
    List<GroupUser> getByUsergroupId(String usergroup);

    List<GroupUser> findByUsername(String username);

    void deleteByUsername (String username);
}