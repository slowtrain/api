/**
* IT Infra 대시보드 컨트롤러 클래스
* @author   이정호
* @since    2019.10.15
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.15   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.licensemgmnt.LicenseMapper;
import com.itmsg.episode.app.asset.management.swcat.SwcatMapper;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIService;
import com.itmsg.episode.app.asset.resource.disci.DisCIService;
import com.itmsg.episode.system.ESApplicationOption;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = AssetDashboardController.URL)
public class AssetDashboardController {
    public static final String APPLICATION = "ASSETDASHBOARD";
    public static final String URL = "/api/asset/dashboard";

    @ESApplicationOption(application = AssetDashboardController.APPLICATION, description = "AssetDashboard read option")
    public static final String READ = "ASSETDASHBOARD_READ";

    @Resource
    private CIService ciService;

    @Resource
    private DisCIService disCIService;

    @Resource
    private CIMapper ciMapper;

    @Resource
    private SwcatMapper swcatMapper;

    @Resource
    private LicenseMapper licenseMapper;

    /**
     * name : getItInfraSituation
     * description : CI 현황
     * @param : String time
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + AssetDashboardController.READ + "')")
    @GetMapping(value="/itinfra")
    @ResponseBody
    public ResponseEntity<?> getItInfraSituation(@RequestParam("cTime") String cTime) {

        Map<String,Object> result = ciService.getItInfraSituation(cTime);

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getScanCI
     * description : IT 인프라 SacnCI 현황
     * @param : null
     * @return : ResponseEntity
     */    
    @PreAuthorize("hasAuthority('" + AssetDashboardController.READ + "')")
    @GetMapping(value="/scanci")
    @ResponseBody
    public ResponseEntity<?> getScanCI() {

        Map<String,Object> result = ciService.getScanCI();

        return ResponseEntity.ok().body(result);

    }

    /**
     * name : getItLicenseSituation
     * description : 라이선스 현황
     * @param : null
     * @return : ResponseEntity
     */  
    @PreAuthorize("hasAuthority('" + AssetDashboardController.READ + "')")
    @GetMapping(value="/license")
    @ResponseBody
    public ResponseEntity<?> getItLicenseSituation() {

        Map<String,Object> result = new HashMap<String,Object>();

        List<Map<String,Object>> company = licenseMapper.companyLicense();
        List<Map<String,Object>> swlictype = licenseMapper.swlictypeLicense();

        result.put("company", company);
        result.put("swlictype", swlictype);

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getJobCI
     * description : IT자원 작업 현황
     * @param : null
     * @return : ResponseEntity
     */  
    @PreAuthorize("hasAuthority('" + AssetDashboardController.READ + "')")
    @GetMapping(value="/jobci")
    @ResponseBody
    public ResponseEntity<?> getJobCI() {

        Map<String,Object> result = new HashMap<String,Object>();

        result.put("mgConfirmData", ciMapper.mgConfirm());
        result.put("swLinkData", ciMapper.swLink());
        result.put("swcatData", swcatMapper.swcatData());

        return ResponseEntity.ok().body(result);
    }

    /**
     * name : getEmsDisCI
     * description : EMS작업 현황
     * @param : null
     * @return : ResponseEntity
     */  
    @PreAuthorize("hasAuthority('" + AssetDashboardController.READ + "')")
    @GetMapping(value="/jobems")
    @ResponseBody
    public ResponseEntity<?> getEmsDisCI() {

        Map<String,Object> result = disCIService.getEmsDisCI();

        return ResponseEntity.ok().body(result);
    }


}