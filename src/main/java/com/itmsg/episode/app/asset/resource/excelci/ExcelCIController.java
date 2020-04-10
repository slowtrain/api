/**
* 소프트웨어 목록 컨트롤러 클래스
* @author   최영훈
* @since    2019.12.26
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.12.26   이정호   최초 생성
*/

package com.itmsg.episode.app.asset.resource.excelci;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.system.classification.ClassificationService;
import com.itmsg.episode.app.system.classification.Classstructure;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = ExcelCIController.URL)
public class ExcelCIController {
    public static final String APPLICATION = "EXCELCI";
    public static final String URL = "/api/asset/resource/excelci";

    @ESApplicationOption(application = ExcelCIController.APPLICATION, description = "EXCELCI read option")
    public static final String READ = "EXCELCI_READ";

    @Resource
    private ClassificationService classificationService;

    @Resource
    private CIMapper ciMapper;


    /**
     * name : list description : software list
     * 
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.EXCELCI_LIST)
    @PreAuthorize("hasAuthority('" + ExcelCIController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(CI.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", ciMapper.list(param));
        param.put("srchtype", "count");
        rtnMap.put("total", ciMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    @PreAuthorize("hasAuthority('" + ExcelCIController.READ + "')")
    @GetMapping(value="/property")
    @ResponseBody
    public ResponseEntity<?> property(@RequestParam("ancestor") String ancestor) {
        
        Classstructure classstructure = classificationService.property(ancestor);

        return ResponseEntity.ok().body(classstructure);
    }
}