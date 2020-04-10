/**
* 어플리케이션 손실비용 SQL Map 클래스
* @author   이정호
* @since    2019.11.25
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.25   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.servicemgmnt.serviceapp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Mapper;
  
@Mapper
@Resource(name="sqlSessionFactory")
public interface ServiceAppMapper {

    List<Map<String,Object>> getAppSystem(Map<String, Object> param);

    List<Map<String, Object>> getServiceAppList(Map<String, Object> param);
    int listTot(Map<String, Object> param);

    Map<String, Object> appData(Map<String, Object> param);

    
}