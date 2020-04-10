/**
* 사이드바  SQL Map 클래스
* @author   이정호
* @since    2019.08.13
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.13   이정호   최초 생성
*/
package com.itmsg.episode.app.sidebar;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SidebarMapper {
    List<Map<String, Object>> getUserCiList(String searchParam, String userId);
}