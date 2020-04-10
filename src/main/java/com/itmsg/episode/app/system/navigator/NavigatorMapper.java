/**
* 메뉴 SQL Map 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.navigator;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.application.Application;

@Mapper
@Resource(name="sqlSessionFactory")
public interface NavigatorMapper {
    List<Navigator> list(Map<String,Object> param);
    List<Application> appSearchList(Map<String,Object> param);
    List<Map<String, Object>> deleteAncestorList(Map<String,Object> param);
    void deleteAncestor(Map<String,Object> param);
    List<Map<String, Object>> mainNaviList(Map<String,Object> param);  
    List<Map<String, Object>> childNodeList(Map<String,Object> param);
    List<Map<String, Object>> treeList(Map<String,Object> param);
      
}

