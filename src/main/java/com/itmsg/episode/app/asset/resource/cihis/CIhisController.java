/**
* IT 자원 변경 이력 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.18
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cihis;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = CIhisController.URL)
public class CIhisController {
    public static final String APPLICATION = "CIHIS";
    public static final String URL = "/api/asset/resource/cihis";

    @ESApplicationOption(application = CIhisController.APPLICATION, description = "CIHIS read option")
    public static final String READ = "CIHIS_READ";
      
    @Resource
    private CIhisMapper cihisMapper;
    
    @Resource
    private CIhisRepository cihisRepository;

    @Resource
    private TextEncryptor textEncryptor;

    // page: cihis, get cihis list
    @DataFilterEnabled(filter = ESDataFilter.CIHIS_LIST)
    @PreAuthorize("hasAuthority('" + CIhisController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(CIhis.class, (String) param.get("orderBy")));
        }
            
        List<CIhis> cihisList = cihisMapper.list(param);   

        for(CIhis cihis : cihisList){
            if(!StringUtils.isNull(cihis.getUpdateNm())){
                cihis.setUpdateNm(textEncryptor.decrypt(cihis.getUpdateNm()));
            }
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", cihisList);
        rtnMap.put("total", cihisMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }     
}