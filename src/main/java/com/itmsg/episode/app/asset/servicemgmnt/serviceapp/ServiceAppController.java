/**
* 어플리케이션 손실비용 컨트롤러 클래스
* @author   이정호
* @since    2019.11.25
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.25   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.servicemgmnt.serviceapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.util.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ServiceAppController.URL)
public class ServiceAppController {
    public static final String APPLICATION = "SERVICEAPP";
    public static final String URL = "/api/asset/servicemgmnt/serviceapp";

    @ESApplicationOption(application = ServiceAppController.APPLICATION, description = "SERVICEAPP read option")
    public static final String READ = "SERVICEAPP_READ";

    @Resource
    private ServiceAppService serviceAppService;

    @Resource
    private ServiceAppMapper serviceAppMapper;

     /**
     * name : getBizList
     * description : 비지니스 어플리케이션 리스트 조회
     * @param : null
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ServiceAppController.READ + "')")   
    @GetMapping(value="/app")
    @ResponseBody
    public ResponseEntity<?> getAppList(@RequestParam Map<String, Object> param) {
        
        List<Map<String, Object>> result = serviceAppMapper.getAppSystem(param);

        return ResponseEntity.ok().body(result);
    }   


    /**
     * name : getServiceAppList
     * description : 어플리케이션 손실비용 리스트 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ServiceAppController.READ + "')")  
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getServiceAppList(@RequestParam Map<String, Object> param) {

        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(EventApp.class, (String) param.get("orderBy")));
        }  
 
        String app = param.get("app").toString();
        String [] appArr = app.split(",");
        
        param.put("appArr", appArr);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", serviceAppMapper.getServiceAppList(param));
        rtnMap.put("total", serviceAppMapper.listTot(param));

          return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : getAppData
     * description : eventApp
     * @param : Map param
     * @return : ResponseEntity
     */ 
    @PreAuthorize("hasAuthority('" + ServiceAppController.READ + "')")  
    @GetMapping(value="/appdata")
    @ResponseBody
    public ResponseEntity<?> getAppData(@RequestParam Map<String, Object> param) {
        
        List<Map<String,Object>> result = serviceAppService.getAppData(param);

        return ResponseEntity.ok().body(result);
    }

}