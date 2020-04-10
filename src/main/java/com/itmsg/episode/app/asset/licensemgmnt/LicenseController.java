/**
* 라이선스 컨트롤러 클래스
* @author   최영훈
* @since    2019.09.16
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.16   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import com.itmsg.episode.app.asset.management.swcat.Swcat;
import com.itmsg.episode.app.asset.management.swcat.SwcatRepository;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = LicenseController.URL)
public class LicenseController {
    public static final String APPLICATION = "LICENSE";
    public static final String URL = "/api/asset/licensemgmnt/license";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License attachment option")
    public static final String ATTACHMENT = "LICENSE_ATTACHMENT";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License read option")
    public static final String READ = "LICENSE_READ";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License add option")
    public static final String ADD = "LICENSE_ADD";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License save option")
    public static final String SAVE = "LICENSE_SAVE";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License delete option")
    public static final String DELETE = "LICENSE_DELETE";
    
    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License swcat add option")
    public static final String SWCAT_ADD = "LICENSE_SWCAT_ADD";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License swcat save option")
    public static final String SWCAT_SAVE = "LICENSE_SWCAT_SAVE";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License swcat delete option")
    public static final String SWCAT_DELETE = "LICENSE_SWCAT_DELETE";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License relation add option")
    public static final String RELATION_ADD = "LICENSE_RELATION_ADD";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License relation save option")
    public static final String RELATION_SAVE = "LICENSE_RELATION_SAVE";

    @ESApplicationOption(application = LicenseController.APPLICATION, description = "License relation delete option")
    public static final String RELATION_DELETE = "LICENSE_RELATION_DELETE";

    @Resource
    private SwLicenseRepository swlicenseRepository;

    @Resource
    private SwLiccatRepository swLiccatRepository;

    @Resource
    private SwLicrelRepository swLicrelRepository;

    @Resource
    private LicenseMapper licenseMapper;

    @Resource
    private SwcatRepository swcatRepository;

    @Resource
    private TextEncryptor textEncryptor;
    
    @Resource
    private SystemCommonService systemCommonService;

    /**
     * name : list
     * description : license list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.LICENSE_LIST)
    @PreAuthorize("hasAuthority('" + LicenseController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(SwLicense.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", licenseMapper.list(param));
        rtnMap.put("total", licenseMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : get
     * description : license one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.LICENSE_LIST)
    @PreAuthorize("hasAuthority('"+ LicenseController.READ +"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        

        SwLicense swLicenseData = licenseMapper.list(param).get(0);

        if(!StringUtils.isNull(swLicenseData.getManagerNm())){
            swLicenseData.setManagerNm(textEncryptor.decrypt(swLicenseData.getManagerNm()));
        }
        if(!StringUtils.isNull(swLicenseData.getCreateNm())){
            swLicenseData.setCreateNm(textEncryptor.decrypt(swLicenseData.getCreateNm()));
        }   
        if(!StringUtils.isNull(swLicenseData.getUpdateNm())){
            swLicenseData.setUpdateNm(textEncryptor.decrypt(swLicenseData.getUpdateNm()));
        }   
        return ResponseEntity.ok().body(swLicenseData);
    }

    /**
     * name : licenseId
     * description : license new id
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LicenseController.READ + "')")
    @GetMapping(value="/licenseId")
    @ResponseBody
    public ResponseEntity<?> licenseId(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(licenseMapper.licenseId(param));
    }

    /**
     * name : post
     * description : license create
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody SwLicense data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        SwLicense rtnData = swlicenseRepository.save(data);
        List<SwLiccat> swcatList = data.getSwcatList();
        for(SwLiccat swLiccat : swcatList){
            swLiccat.setCreateId(userDetails.getUsername());
            swLiccat.setUpdateId(userDetails.getUsername());
            swLiccatRepository.save(swLiccat);
        }
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : put
     * description : license modify
     * @param : SwLicense data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody SwLicense data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(swlicenseRepository.save(data));
    }

    /**
     * name : delete
     * description : license delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<SwLicense> swLicense = swlicenseRepository.findById(id);
        if(!swLicense.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        swlicenseRepository.deleteById(id);    
        swLicrelRepository.deleteByOrgIdAndSwlicenseId2(swLicense.get().getOrgId(), swLicense.get().getSwlicenseId());    
        return ResponseEntity.ok().build();
    }

    /**
     * name : getRelation
     * description : relation license list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ LicenseController.READ +"')")
    @GetMapping(value="/{id}/relation")
    @ResponseBody
    public ResponseEntity<?> getRelation(@PathVariable("id") Long id){
        Optional<SwLicense> swLicense = swlicenseRepository.findById(id);
        if(!swLicense.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("swlicenseId", swLicense.get().getSwlicenseId());
        
        List<SwLicense> swLicrelList = licenseMapper.relationList(param);
        return ResponseEntity.ok().body(swLicrelList);
    }

    /**
     * name : postRelation
     * description : relation license create
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.RELATION_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/relation")
    @ResponseBody
    public ResponseEntity<?> postRelation(@RequestBody List<SwLicrel> dataList, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(SwLicrel data : dataList){
            if(data.getId() == null){
                data.setCreateId(userDetails.getUsername());
            }     
            data.setUpdateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(swLicrelRepository.saveAll(dataList));
    }

    /**
     * name : deleteRelation
     * description : relation license delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.RELATION_DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/relation/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRelation(@PathVariable("id") Long id) throws Exception{
        swLicrelRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }

    /**
     * name : getSwcatList
     * description : relation software catalogue list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ LicenseController.READ +"')")
    @GetMapping(value="/{id}/swcat")
    @ResponseBody
    public ResponseEntity<?> getSwcatList(@PathVariable("id") Long id){
        Optional<SwLicense> swLicense = swlicenseRepository.findById(id);
        if(!swLicense.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("swlicenseId", swLicense.get().getSwlicenseId());
        
        List<Swcat> swcatList = licenseMapper.swcatList(param);
        return ResponseEntity.ok().body(swcatList);
    }

    /**
     * name : chkDupSwcat
     * description : chkDupSwcat
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ LicenseController.READ +"')")
    @GetMapping(value="/chkdupswcat")
    @ResponseBody
    public ResponseEntity<?> chkDupSwcat(@RequestParam Map<String, Object> param){        
            String[] targetChkList = null;
            targetChkList = param.get("targetChk").toString().split(",");
            param.put("targetChkList", targetChkList);
        return ResponseEntity.ok().body(licenseMapper.chkDupSwcat(param));
    }    

    /**
     * name : postSwcat
     * description : relation software catalogue save
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.SWCAT_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/swcat")
    @ResponseBody
    public ResponseEntity<?> postSwcat(@RequestBody List<SwLiccat> dataList, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(SwLiccat data : dataList){
            if(data.getId() == null){
                data.setCreateId(userDetails.getUsername());
            }     
            data.setUpdateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(swLiccatRepository.saveAll(dataList));
    }

    /**
     * name : postSwcat
     * description : relation software catalogue delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+LicenseController.SWCAT_DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/swcat/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSwcat(@PathVariable("id") Long id) throws Exception{
        swLiccatRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }
}