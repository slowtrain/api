/**
* 이벤트 서비스 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/  
package com.itmsg.episode.app.event;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    
    private static final String [] RESOURCES = {"SMS", "NMS", "APM", "DBMS"};

    @Resource
    private EventMapper eventMapper;

     /**
     * name : getDashboardEvent
     * description : 카테고리별 데이터를 조회하는 기능
     * @param : null
     * @return : ResponseEntity
     */   
    public Map<String, Object> getDashboardEvent(String cTime, String delay) {

        Map<String,Object> result = new HashMap<>();
        List<List<Map<String, Object>>> ciEvent = null;
        for(String resource : RESOURCES) {
            ciEvent = new ArrayList<>();
            ciEvent.add(eventMapper.getEventCount(resource));
            ciEvent.add(eventMapper.getEventData(resource, cTime));
            result.put(resource, ciEvent);
        }

        ciEvent = new ArrayList<>();
        ciEvent.add(eventMapper.getDelayEventCount(delay));
        ciEvent.add(eventMapper.getDelayEventData(cTime));
        result.put("DELAY", ciEvent);
        return result;
    }

}
