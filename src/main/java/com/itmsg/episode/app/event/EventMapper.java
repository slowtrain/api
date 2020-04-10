/**
* 이벤트  SQL Map 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.event;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface EventMapper {
    // List<Event> list(Map<String, String> param);
    List<Map<String, Object>> getEventList(Map<String,Object> param);
    List<Map<String, Object>> getSystemEvent(Map<String,Object> param);
    List<Map<String, Object>> getDelayEvent(Map<String,Object> param);
    List<Map<String, Object>> getBizEvent(Map<String,Object> param);
    List<Map<String, Object>> getCiEvent(String ciId);
    List<Map<String, Object>> getTopologyEvent(String ciId);
    List<Map<String, Object>> getEvent();
    List<Map<String, Object>> getEventCount(String resource);
    List<Map<String, Object>> getEventData(String resource, String cTime);
    List<Map<String, Object>> getBizEventData();
    List<Map<String, Object>> getBizCiEventData(String bizCiId);
    List<Map<String, Object>> getDelayEventCount(String delay);
    List<Map<String, Object>> getDelayEventData(String cTime);
    List<Map<String, Object>> getEventCiIdList();
    List<Map<String, Object>> getBusinessEventCount();
    List<Map<String, Object>> getBusinessEventData();
    int listTot(Map<String, Object> param);

    List<Map<String, Object>> eventhisList(Map<String, Object> param);

    
}