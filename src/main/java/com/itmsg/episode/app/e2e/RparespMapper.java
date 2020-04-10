/**
* Rpa  SQL Map 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.e2e;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface RparespMapper {
    Map<String,Object> getLocationRpa(String ciId, String bizId);
    List<Map<String, Object>> getRpaSystem();
    List<Map<String, Object>> getBizList();
    
    List<Map<String, Object>> getRpaTopList(String cTime);
    List<Map<String, Object>> getE2EList(Map<String, Object> param);
    int listTot(Map<String, Object> param);

    Map<String, Object> rpaData(Map<String, Object> param);

    Map<String, Object> getBizDt();
    
    
    
}