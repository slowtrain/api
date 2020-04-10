/**
* 이벤트 컨트롤러 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.event;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.app.system.classification.ClassificationMapper;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value= EventController.URL)
public class EventController {
    public static final String APPLICATION="EVENT";
    public static final String URL="/api/event";

    @ESApplicationOption(application = EventController.APPLICATION, description = "Event read option")
    public static final String READ = "EVENT_READ";

    @Resource
    private EventService eventService;

    @Resource
    private EventMapper eventMapper;

    @Resource
    CIRepository ciRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private ClassificationMapper classificationMapper;

    @Resource
    private CIMapper ciMapper;

    @Resource
    private EventRepository eventRepository;


    /**
     * name : getCiData
     * description : 선택된 ci 데이터 조회
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/cidata/{id}")
    @ResponseBody
    public ResponseEntity<?> getCiData(@PathVariable("id") Long id){

        Optional<CI> ci = ciRepository.findById(id);

        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> result = new HashMap<String, Object>();

        String managerName = null;
        
        if(ci.get().getManagerId() != null && !ci.get().getManagerId().equals("")){

            User user = userRepository.findByUsername(ci.get().getManagerId());
        
            if(user.getFirstname() != null && !user.getFirstname().equals("")){
                managerName = textEncryptor.decrypt(user.getFirstname());
            }
        }
          
        result.put("system", classificationMapper.findParent(ci.get().getClassstructureId()));
        result.put("ci",ci.get());
        result.put("managerName",managerName);

        return ResponseEntity.ok().body(result);
        
    }


    /**
     * name : getCiEvnet
     * description : 선택된 ci 관련이벤트 조회
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/cievent/{id}")
    @ResponseBody
    public ResponseEntity<?> getCiEvnet(@PathVariable("id") Long id){

        Optional<CI> ci = ciRepository.findById(id);

        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        String parent = classificationMapper.findParent(ci.get().getClassstructureId());
        
        if(parent.equals("SW_MIDDLESW_WAS")  || parent.equals("SW_DBSW")) {

            CI relCI = ciMapper.getRelCi(ci.get().getCiId());

            return ResponseEntity.ok().body(eventMapper.getCiEvent(relCI.getCiId()));

        } else {
            
            return ResponseEntity.ok().body(eventMapper.getCiEvent(ci.get().getCiId()));
        }


        
    }

    /**
     * name : getTopologyEvent
     * description : 선택된 토플리지 ci 이벤트 조회
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/topologyevent/{id}")
    @ResponseBody
    public ResponseEntity<?> getTopologyEvent(@PathVariable("id") Long id){
       Optional<CI> ci = ciRepository.findById(id);
       if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().body(eventMapper.getTopologyEvent(ci.get().getCiId()));
       
    }

    /**
     * name : getSystemEvent
     * description : 시스템 이벤트 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/systemevent")
    @ResponseBody
    public ResponseEntity<?> getSystemEvent(@RequestParam Map<String, Object> param, HttpServletRequest request) {
 
        if (param.get("ciSystem").toString().equals("") || param.get("ciSystem") == null) { 
          return ResponseEntity.ok().body(eventMapper.getDelayEvent(param));
        } else {
          return ResponseEntity.ok().body(eventMapper.getSystemEvent(param));
        }
    }

    /**
     * name : getBizEvent
     * description : 서비스 이벤트 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/bizevent")
    @ResponseBody
    public ResponseEntity<?> getBizEvent(@RequestParam Map<String, Object> param, HttpServletRequest request) {
 
        return ResponseEntity.ok().body(eventMapper.getBizEvent(param));
    }
   

    /**
     * name : getSystemEvent
     * description : 이벤트히스토리 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + EventController.READ + "')")
    @GetMapping(value="/eventhis")
    @ResponseBody
    public ResponseEntity<?> getEventhisList(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Eventhis.class, (String) param.get("orderBy")));
        }
 
        String severity = param.get("severity").toString();
        String [] severityArr = severity.split(",");
        param.put("severityArr",severityArr);

        String ciType = param.get("ciType").toString();
        String [] ciTypeArr = ciType.split(",");
        param.put("ciTypeArr",ciTypeArr);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", eventMapper.eventhisList(param));
        rtnMap.put("total", eventMapper.listTot(param));

          return ResponseEntity.ok().body(rtnMap);
    }



}