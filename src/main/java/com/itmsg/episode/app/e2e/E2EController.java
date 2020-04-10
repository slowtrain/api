/**
* E2E 컨트롤러 클래스
* @author   이정호
* @since    2019.09.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.23   이정호   최초 생성
*/
package com.itmsg.episode.app.e2e;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.app.asset.location.LocationMapper;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.util.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = E2EController.URL)
public class E2EController {
    public static final String APPLICATION = "E2E";
    public static final String URL = "/api/e2e";

    @ESApplicationOption(application = E2EController.APPLICATION, description = "E2E read option")
    public static final String READ = "E2E_READ";

    @Resource
    private CIMapper ciMapper;

    @Resource
    private LocationMapper locationMapper;

    @Resource
    private RparespService rparespService;

    @Resource
    private RparespMapper rparespMapper;

    /**
     * name : getBizList
     * description : 비지니스 어플리케이션 리스트 조회
     * @param : null
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + E2EController.READ + "')")   
    @GetMapping(value="/biz")
    @ResponseBody
    public ResponseEntity<?> getBizList() {
        
        List<Map<String, Object>> result = rparespMapper.getBizList();

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getRpaList
     * description : RPA시스템 리스트 조회
     * @param : null
     * @return : ResponseEntity
     */ 
    @PreAuthorize("hasAuthority('" + E2EController.READ + "')")  
    @GetMapping(value="/rpa")
    @ResponseBody
    public ResponseEntity<?> getRpaList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        
        List<Map<String, Object>> result = rparespMapper.getRpaSystem();

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getRpaData
     * description : RPA데이터 리스트 조회
     * @param : Map param
     * @return : ResponseEntity
     */ 
    @PreAuthorize("hasAuthority('" + E2EController.READ + "')")  
    @GetMapping(value="/rpadata")
    @ResponseBody
    public ResponseEntity<?> getRpaData(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        
        List<Map<String,Object>> result = rparespService.getRpaData(param);

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getE2EList
     * description : E2E 리스트 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + E2EController.READ + "')")  
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getE2EList(@RequestParam Map<String, Object> param) {

        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Rparespraw.class, (String) param.get("orderBy")));
        }  
 
        String rpa = param.get("rpa").toString();
        String [] rpaArr = rpa.split(",");
        
        param.put("rpaArr",rpaArr);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", rparespMapper.getE2EList(param));
        rtnMap.put("total", rparespMapper.listTot(param));

          return ResponseEntity.ok().body(rtnMap);
    }

}