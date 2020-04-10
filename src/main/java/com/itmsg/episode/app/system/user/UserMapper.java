/**
* 유저 SQL Map 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.role.Role;

@Mapper
@Resource(name = "sqlSessionFactory")
public interface UserMapper {
    public List<User> list(Map<String, Object> param);

    int listTot(Map<String, Object> param);

    public List<UserAuthority> getUserAuthorities(User user);

    public List<Role> getUserRole(User user);

    public List<UserFilter> getUserFilter(Map<String, String> param);
}
