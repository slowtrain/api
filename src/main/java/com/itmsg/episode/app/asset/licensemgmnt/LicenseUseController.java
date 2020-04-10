/**
* 라이선스사용 컨트롤러 클래스
* @author   최영훈
* @since    2019.09.30
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.30   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import com.itmsg.episode.app.asset.management.swcat.Swcat;
import com.itmsg.episode.app.asset.resource.ci.CI;
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
@RequestMapping(value = LicenseUseController.URL)
public class LicenseUseController {
    public static final String APPLICATION = "LICENSEUSE";
    public static final String URL = "/api/asset/licensemgmnt/licenseuse";

    @ESApplicationOption(application = LicenseUseController.APPLICATION, description = "LicenseUseController read option")
    public static final String READ = "LICENSEUSE_READ";

    @Resource
    private SwLicenseUseRepository swLicenseUseRepository;
    
    @Resource
    private LicenseUseMapper licenseUseMapper;
    
    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : licenseuse list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.LICENSEUSE_LIST)
    @PreAuthorize("hasAuthority('" + LicenseUseController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Swcat.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", licenseUseMapper.list(param));
        rtnMap.put("total", licenseUseMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : list
     * description : licenseuse use server list
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ LicenseController.READ +"')")
    @GetMapping(value="/serverlist")
    @ResponseBody
    public ResponseEntity<?> serverList(@RequestParam Map<String, Object> param){
        List<CI> serverList = licenseUseMapper.serverList(param);

        for(CI server : serverList){
            if(!StringUtils.isNull(server.getManagerNm())){
                server.setManagerNm(textEncryptor.decrypt(server.getManagerNm()));
            }
        }

        return ResponseEntity.ok().body(serverList);
    }
}