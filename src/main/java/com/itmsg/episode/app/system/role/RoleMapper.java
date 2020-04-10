/**
* 롤 SQL Map 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import com.itmsg.episode.app.system.application.Authority;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.usergroup.UserGroup;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface RoleMapper {
    // Role
    List<Role> roleList(Map<String, Object> param);
    Map<String, Object> childDataCount (String roleId);

    // Role Auth
    List<RoleAuth> roleAuthList (Map<String, Object> param);
    List<Authority> authList (Map<String, Object> param);
    List<Map<String, Object>> userAuthMapping(Map<String, Object> param);
    List<Map<String, Object>> authByRole(Map<String, Object> param);
    void authDeleteAll(String roleId);

    // Role Filter
    List<RoleFilter> roleFilterList (Map<String, Object> param);
    List<Map<String, Object>> userFilterMapping (Map<String, Object> param);
    List<Map<String, Object>> filterByRole(Map<String, Object> param);
    void filterDeleteAll(String roleId);


    // Role User
    List<User> users(Map<String, String> param);
    List<Map<String, Object>> getRoleUsers(String ROLEID);

    // Role User Group
    List<Map<String, Object>> getUserGroupList(String roleId);
    List<UserGroup> getGroups(Map<String, Object> param);

}
