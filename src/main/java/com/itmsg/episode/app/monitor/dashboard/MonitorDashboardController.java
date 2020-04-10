/**
* 모니터 대시보드 컨트롤러 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.monitor.dashboard;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.e2e.RparespService;
import com.itmsg.episode.app.event.EventMapper;
import com.itmsg.episode.app.event.EventService;
import com.itmsg.episode.app.system.domain.DomainMapper;
import com.itmsg.episode.app.system.organization.OrganizationRepository;
import com.itmsg.episode.app.system.organization.OrgvariableRepository;
import com.itmsg.episode.system.ESApplicationOption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = MonitorDashboardController.URL)
public class MonitorDashboardController {
    public static final String APPLICATION = "MONITORDASHBOARD";
    public static final String URL = "/api/monitor/dashboard";

    @ESApplicationOption(application = MonitorDashboardController.APPLICATION, description = "MonitorDashboard read option")
    public static final String READ = "MONITORDASHBOARD_READ";

    @Resource
    private EventService eventService;

    @Resource
    private RparespService rparespService;

    @Resource
    private EventMapper eventMapper;


    /**
     * name : getDashboardEvent
     * description : 카테고리별 이벤트 현황 및 이벤트 히스토리 차트 데이터 조회
     * @param : null
     * @return : ResponseEntity
     */
    @GetMapping(value="/event")
    @ResponseBody
    public ResponseEntity<?> getDashboardEvent(@RequestParam("cTime") String cTime, @RequestParam("delay") String delay) {
        
        Map<String,Object> result = eventService.getDashboardEvent(cTime, delay);

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getEventBiz
     * description : 서비스 이벤트 현황 조회
     * @param : null
     * @return : ResponseEntity
     */
    @GetMapping(value="/eventbiz")
    @ResponseBody
    // public ResponseEntity<?> getEventBiz(@RequestParam("cTime") String cTime, @RequestParam("delay") String delay) {
    public ResponseEntity<?> getEventBiz() {
        
        return ResponseEntity.ok().body(eventMapper.getBusinessEventCount());
    }

    /**
     * name : getBizEventData
     * description : 서비스 심각도 데이터 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @GetMapping(value="/bizdata")
    @ResponseBody
    public ResponseEntity<?> getBizEventData() {
 
        return ResponseEntity.ok().body(eventMapper.getBizEventData());
    }

    /**
     * name : getBizCiEventData
     * description : 해당 서비스 심각도 데이터 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @GetMapping(value="/bizcidata")
    @ResponseBody
    public ResponseEntity<?> getBizCiEventData(@RequestParam("bizCiId") String bizCiId) {
 
        return ResponseEntity.ok().body(eventMapper.getBizCiEventData(bizCiId));
    }



    /**
     * name : getRparespList
     * description : 위치별, 업무 응답시간 데이터 조회
     * @param : null
     * @return : ResponseEntity
     */
    @GetMapping(value="/rparesp")
    @ResponseBody
    public ResponseEntity<?> getRparespList() {

        Map<String,Object> result = rparespService.getRparespList();

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getRpaTopList
     * description : 업무 응답시간 TOP 10 데이터 조회
     * @param : null
     * @return : ResponseEntity
     */
    @GetMapping(value="/rpaTop")
    @ResponseBody
    public ResponseEntity<?> getRpaTopList(@RequestParam("cTime") String cTime) {

        List<Map<String, Object>> result = rparespService.getRpaTopList(cTime);

        return ResponseEntity.ok().body(result);
    }


}