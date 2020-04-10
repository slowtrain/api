/**
* 모니터 대시보드 컨트롤러 클래스
* @author   이정호
* @since    2019.09.02
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.02   이정호   최초 생성
*/
package com.itmsg.episode.app.monitor.detail;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.app.system.classification.ClassificationMapper;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.system.ESApplicationOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = MonitorDetailController.URL)
public class MonitorDetailController {
    public static final String APPLICATION = "MONITORDETAIL";
    public static final String URL = "/api/monitor/detail";

    @ESApplicationOption(application = MonitorDetailController.APPLICATION, description = "MonitorDetail read option")
    public static final String READ = "MONITORDETAIL_READ";


    @Resource
    private PerfrawMapper perfrawMapper;

    @Resource
    private CIRepository ciRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private ClassificationMapper classificationMapper;

    @Resource
    private CIMapper ciMapper;

    /**
     * name : getCiData
     * description : 모니터링 상세 시스템 및 CI 내역
     * @param : Long id
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/cidata/{id}")
    @ResponseBody
    public ResponseEntity<?> getCiData(@PathVariable("id") Long id){

        Optional<CI> ci = ciRepository.findById(id);

        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> result = new HashMap<String, Object>();

        String managerName = null;

        String parent = classificationMapper.findParent(ci.get().getClassstructureId());
        
        if (parent != null) {
        if (parent.equals("SW_MIDDLESW")  || parent.equals("SW_DBSW")) {
            CI relCI = ciMapper.getRelCi(ci.get().getCiId());
            if (relCI != null) {
              result.put("system", classificationMapper.findParent(relCI.getClassstructureId()));    
              result.put("ci",relCI);

            } else {
              result.put("system", parent);
              String classstructureNm = ciMapper.getClassstructureNm(ci.get().getOrgId(), ci.get().getClassstructureId());
              ci.get().setClassstructureNm(classstructureNm);
              result.put("ci",ci.get());
            }

        } else {
          result.put("system", parent);
          String classstructureNm = ciMapper.getClassstructureNm(ci.get().getOrgId(), ci.get().getClassstructureId());
          ci.get().setClassstructureNm(classstructureNm);
          result.put("ci",ci.get());

        }
    } else {
        result.put("system", ci.get().getClassstructureId());
        String classstructureNm = ciMapper.getClassstructureNm(ci.get().getOrgId(), ci.get().getClassstructureId());
        ci.get().setClassstructureNm(classstructureNm);
        result.put("ci",ci.get());
    }
        
       if(((CI) result.get("ci")).getManagerId() != null && !((CI) result.get("ci")).getManagerId().equals("")){
            User user = userRepository.findByUsername(((CI) result.get("ci")).getManagerId());
            if(user.getFirstname() != null && !user.getFirstname().equals("")){
                managerName = textEncryptor.decrypt(user.getFirstname());
            }
        }
        
        result.put("managerName",managerName);

        return ResponseEntity.ok().body(result);
 
        
    }

    /**
     * name : getServerPerf
     * description : CI 성능
     * @param : Map param
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/emsperf")
    public ResponseEntity<?> getServerPerf(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        Map<String, Object> pd = perfrawMapper.serverPerf1(param);
        Map<String, Object> day = perfrawMapper.serverPerf2(param);
        
        Map<String, Object> r = new HashMap<>();
        
        r.put("previousDay", pd);
        r.put("day", day);

        return ResponseEntity.ok().body(r);
    }

    /**
     * name : getServerPerfAdd
     * description : CI 성능 추가
     * @param : Map param
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/emsperfadd")
    public ResponseEntity<?> getServerPerfAdd(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        Map<String, Object> pd = perfrawMapper.serverPerfAdd1(param);
        Map<String, Object> day = perfrawMapper.serverPerfAdd2(param);
        
        Map<String, Object> r = new HashMap<>();
        
        r.put("previousDay", pd);
        r.put("day", day);

        return ResponseEntity.ok().body(r);
    }

    /**
     * name : getServerDisk
     * description : CI 디스크 현황
     * @param : Map param
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/emsdisk")
    public ResponseEntity<?> getServerDisk(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        return ResponseEntity.ok().body(perfrawMapper.getDisk(param));
    }

    /**
     * name : getServerDisk
     * description : CI EMS 현황
     * @param : Map param
     * @return : ResponseEntity
     */  
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/multiplems")
    public ResponseEntity<?> getMultipleEms(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        return ResponseEntity.ok().body(perfrawMapper.multipleEmsData(param));
    }

    /**
     * name : getServerDisk
     * description : CI EMS 현황 추가
     * @param : Map param
     * @return : ResponseEntity
     */  
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/multiplemsadd")
    public ResponseEntity<?> getMultipleEmsAdd(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        return ResponseEntity.ok().body(perfrawMapper.multipleEmsAdd(param));
    }
    
    /**
     * name : getRelationEms
     * description : CI Relation 현황
     * @param : Map param
     * @return : ResponseEntity
     */ 
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/relationems")
    public ResponseEntity<?> getRelationEms(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        return ResponseEntity.ok().body(perfrawMapper.relationData(param));
    }

    /**
     * name : getRelationEms
     * description : CI Relation 추가
     * @param : Map param
     * @return : ResponseEntity
     */ 
    @PreAuthorize("hasAuthority('" + MonitorDetailController.READ + "')")
    @GetMapping(value="/relationadd")
    public ResponseEntity<?> getRelationAdd(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        return ResponseEntity.ok().body(perfrawMapper.relationAdd(param));
    }
}