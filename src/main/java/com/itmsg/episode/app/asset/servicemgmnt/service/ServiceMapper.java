/**
* 서비스 SQL Map 클래스
* @author   최영훈
* @since    2019.11.04
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.04   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.servicemgmnt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIrelation;

import org.apache.ibatis.annotations.Mapper;
  
@Mapper
@Resource(name="sqlSessionFactory")
public interface ServiceMapper {
    List<CI> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
    List<CIrelation> relationList(Map<String, Object> param);  
}