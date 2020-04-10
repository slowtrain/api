/**
* IT 자원 변경 이력 SQL Map 클래스
* @author   최영훈
* @since    2019.11.18
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cihis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.cihis.CIhis;

import org.apache.ibatis.annotations.Mapper;
  
@Mapper
@Resource(name="sqlSessionFactory")
public interface CIhisMapper {
    List<CIhis> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);    
}