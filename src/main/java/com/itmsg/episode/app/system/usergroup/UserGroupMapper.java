/**
* 유저 그룹 SQL Map 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import com.itmsg.episode.app.system.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface UserGroupMapper {
    // UserGroup
    List<UserGroup> userGroupList(Map<String, Object> param);
    List<UserGroupAncestor> userGroupAncestorList(Map<String, Object> param);
    Map<String, Object> childDataCount (Map<String, Object> param);
	Integer listTot(Map<String, Object> param);
	
    // UserGroup User
    List<User> users(Map<String, Object> param);
	List<String> getGroupUserSkype(String userGroup);

    List<String> getGroupUserEmail(String userGroup);

    List<String> getGroupChiefs(@Param("username") String username);

    List<String> getGroupUserByUsername(@Param("username") String username);

    List<String> getGroupUserByUsergroupId(@Param("usergroupId") String usergroupId);

}
