/**
* 어플리케이션 권한 SQL Map 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.user.User;

@Mapper
@Resource(name="sqlSessionFactory")
public interface AuthorityMapper {
    List<Map<String,Object>> userAuthApp(User user);
    List<Authority> list(Map<String,Object> param);
}
