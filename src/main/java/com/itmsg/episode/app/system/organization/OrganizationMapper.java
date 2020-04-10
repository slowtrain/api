/**
* 조직  SQL Map 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.organization;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface OrganizationMapper {
    List<Organization> list(Map<String,Object> param);
    String getNotiGroups(String notiType); 
}
