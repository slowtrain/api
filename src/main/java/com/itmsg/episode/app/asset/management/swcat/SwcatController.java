/**
* 소프트웨어 카탈로그 컨트롤러 클래스
* @author   최영훈
* @since    2019.10.14
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.14   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.swcat;

import com.itmsg.episode.app.asset.licensemgmnt.SwLicense;
import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.security.JwtUser;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = SwcatController.URL)
public class SwcatController {
    public static final String APPLICATION = "SWCAT";
    public static final String URL = "/api/asset/management/swcat";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT attachment option")
    public static final String ATACHMENT = "SWCAT_ATACHMENT";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT read option")
    public static final String READ = "SWCAT_READ";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT add option")
    public static final String ADD = "SWCAT_ADD";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT save option")
    public static final String SAVE = "SWCAT_SAVE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT delete option")
    public static final String DELETE = "SWCAT_DELETE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT child add option")
    public static final String CHILD_ADD = "SWCAT_CHILD_ADD";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT child save option")
    public static final String CHILD_SAVE = "SWCAT_CHILD_SAVE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT child delete option")
    public static final String CHILD_DELETE = "SWCAT_CHILD_DELETE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT group add option")
    public static final String GROUP_ADD = "SWCAT_GROUP_ADD";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT group save option")
    public static final String GROUP_SAVE = "SWCAT_GROUP_SAVE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT group delete option")
    public static final String GROUP_DELETE = "SWCAT_GROUP_DELETE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT relation add option")
    public static final String RELATION_ADD = "SWCAT_RELATION_ADD";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT relation save option")
    public static final String RELATION_SAVE = "SWCAT_RELATION_SAVE";

    @ESApplicationOption(application = SwcatController.APPLICATION, description = "SWCAT relation delete option")
    public static final String RELATION_DELETE = "SWCAT_RELATION_DELETE";

    @Resource
    private SwcatRepository swcatRepository;

    @Resource
    private SwcatrelRepository swcatrelRepository;

    @Resource
    private SwcatgrpRepository swcatgrpRepository;

    @Resource
    private CIRepository ciRepository;

    @Resource
    private SwcatMapper swcatMapper;

    @Resource
    private CIMapper ciMapper;

    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list 
     * description : software catalogue list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SWCAT_LIST)
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        if (param.get("swcattype") != null) {
            String[] swcattypeList = null;
            swcattypeList = param.get("swcattype").toString().split(",");
            param.put("swcattypeList", swcattypeList);
        }
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        if (param.get("orderBy") != null) {
            param.put("orderBy", StringUtils.changeColName(Swcat.class, (String) param.get("orderBy")));
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", swcatMapper.list(param));
        rtnMap.put("total", swcatMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : get 
     * description : software catalogue one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SWCAT_LIST)
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request) {
        JwtUser userDetails = (JwtUser) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        
        Optional<Swcat> swcat = swcatRepository.findById(id);
        if (!swcat.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Swcat swcatData = swcat.get();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", userDetails.getOrgId());
        param.put("hierarchypath", swcatData.getHierarchypath());

        List<Swcat> childList = swcatMapper.list(param);

        for (Swcat child : childList) {
            if (!StringUtils.isNull(child.getCreateNm())) {
                child.setCreateNm(textEncryptor.decrypt(child.getCreateNm()));
            }
            if (!StringUtils.isNull(child.getUpdateNm())) {
                child.setUpdateNm(textEncryptor.decrypt(child.getUpdateNm()));
            }
        }
        return ResponseEntity.ok().body(childList);
    }

    /**
     * name : swcatId 
     * description : software catalogue new id
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/swcatId")
    @ResponseBody
    public ResponseEntity<?> swcatId(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(swcatMapper.swcatId(param));
    }

    /**
     * name : post 
     * description : software catalogue create
     * @param : Swcat data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Swcat data, HttpServletRequest request) throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(swcatRepository.save(data));
    }

    /**
     * name : put 
     * description : software catalogue modify
     * @param : Swcat data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Swcat data, HttpServletRequest request) throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setUpdateId(userDetails.getUsername());
        Swcat rtnData = swcatRepository.save(data);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", data.getOrgId());
        param.put("swcatId", data.getSwcatId());

        List<Swcat> swcatList = swcatMapper.childrendata(param);
        for (Swcat swcat : swcatList) {
            swcat.setSwcatname(data.getSwcatname());
            swcat.setClassstructureId(data.getClassstructureId());
            swcat.setIsdomestic(data.getIsdomestic());
            swcat.setPublishername(data.getPublishername());
            swcat.setUpdateId(userDetails.getUsername());
            swcatRepository.save(swcat);
        }

        Swcat childData = data.getChild();
        if(!StringUtils.isNull(childData.getSwcatId())){
            childData.setSwcatname(data.getSwcatname());
            childData.setClassstructureId(data.getClassstructureId());
            childData.setIsdomestic(data.getIsdomestic());
            childData.setPublishername(data.getPublishername());
            childData.setCreateId(userDetails.getUsername());
            childData.setUpdateId(userDetails.getUsername());
            swcatRepository.save(childData);
        }    
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : delete 
     * description : software catalogue delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.DELETE + "')")
    @Transactional(value = "transactionManager")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        swcatRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : relationlist 
     * description : relation catalogue list
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SWCAT_LIST)
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/{id}/relationlist")
    @ResponseBody
    public ResponseEntity<?> relationlist(@PathVariable("id") Long id, HttpServletRequest request) {
        Optional<Swcat> swcat = swcatRepository.findById(id);
        if (!swcat.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", swcat.get().getOrgId());
        param.put("swcatId", swcat.get().getSwcatId());

        List<Swcat> relationList = swcatMapper.relationList(param);

        for (Swcat relatio : relationList) {
            if (!StringUtils.isNull(relatio.getUpdateNm())) {
                relatio.setUpdateNm(textEncryptor.decrypt(relatio.getUpdateNm()));
            }
        }
        return ResponseEntity.ok().body(relationList);
    }

    /**
     * name : postRelation 
     * description : relation catalogue save
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.RELATION_SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping("/relation")
    @ResponseBody
    public ResponseEntity<?> postRelation(@RequestBody List<Swcatrel> dataList, HttpServletRequest request)
            throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Map<String, Object> param = new HashMap<String, Object>();
        List<Swcat> swcatList = null;
        Swcatrel swcatrel = null;
        List<Swcatrel> saveList = new ArrayList<Swcatrel>();
        for (Swcatrel data : dataList) {
            param.put("orgId", data.getOrgId());
            param.put("hierarchypath", "/" + data.getSwcatId2());
            swcatList = swcatMapper.list(param);
            for (Swcat swcat : swcatList) {
                swcatrel = new Swcatrel();
                swcatrel.setOrgId(data.getOrgId());
                swcatrel.setSwcatId1(data.getSwcatId1());
                swcatrel.setSwcatId2(swcat.getSwcatId());
                swcatrel.setCreateId(userDetails.getUsername());
                swcatrel.setUpdateId(userDetails.getUsername());
                saveList.add(swcatrel);
            }
        }
        return ResponseEntity.ok().body(swcatrelRepository.saveAll(saveList));
    }

    /**
     * name : deleteRelation 
     * description : relation catalogue delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.RELATION_DELETE + "')")
    @Transactional(value = "transactionManager")
    @DeleteMapping(value = "/relation")
    @ResponseBody
    public ResponseEntity<?> deleteRelation(@RequestParam Map<String, Object> param) throws Exception {
        String path = (param.get("hierarchypath")) + "/";
        String root = path.substring(0, path.indexOf("/", 2));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgId", param.get("orgId"));
        params.put("hierarchypath", root);
        List<Swcat> swcatList = swcatMapper.list(params);
        for (Swcat swcatData : swcatList) {
            swcatrelRepository.deleteBySwcatId1AndSwcatId2((String)param.get("swcatId"), swcatData.getSwcatId());
            swcatrelRepository.deleteBySwcatId1AndSwcatId2(swcatData.getSwcatId(), (String)param.get("swcatId"));
        }
        return ResponseEntity.ok().build();
    }

    /**
     * name : useserverlist 
     * description : use server list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/{id}/useserverlist")
    @ResponseBody
    public ResponseEntity<?> useserverlist(@PathVariable("id") Long id) {
        Optional<Swcat> swcat = swcatRepository.findById(id);
        if (!swcat.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", swcat.get().getOrgId());
        param.put("swcatId", swcat.get().getSwcatId());

        List<CI> serverList = swcatMapper.serverList(param);

        for (CI server : serverList) {
            if (!StringUtils.isNull(server.getManagerNm())) {
                server.setManagerNm(textEncryptor.decrypt(server.getManagerNm()));
            }
        }
        return ResponseEntity.ok().body(serverList);
    }

    /**
     * name : licenselist 
     * description : relation license list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/{id}/licenselist")
    @ResponseBody
    public ResponseEntity<?> licenselist(@PathVariable("id") Long id) {
        Optional<Swcat> swcat = swcatRepository.findById(id);
        if (!swcat.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", swcat.get().getOrgId());
        param.put("swcatId", swcat.get().getSwcatId());

        List<SwLicense> serverList = swcatMapper.licenseList(param);

        return ResponseEntity.ok().body(serverList);
    }

    /**
     * name : rootdata 
     * description : root data
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/rootdata")
    @ResponseBody
    public ResponseEntity<?> rootdata(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        return ResponseEntity.ok().body(swcatMapper.rootdata(param));
    }

    /**
     * name : groupist 
     * description : group list
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SWCAT_LIST)
    @PreAuthorize("hasAuthority('" + SwcatController.READ + "')")
    @GetMapping(value = "/{id}/grouplist")
    @ResponseBody
    public ResponseEntity<?> groupist(@PathVariable("id") Long id, HttpServletRequest request) {
        Optional<Swcat> swcat = swcatRepository.findById(id);
        if (!swcat.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", swcat.get().getOrgId());
        param.put("swcatId", swcat.get().getSwcatId());

        List<Swcat> groupList = swcatMapper.groupList(param);

        for (Swcat group : groupList) {
            if (!StringUtils.isNull(group.getUpdateNm())) {
                group.setUpdateNm(textEncryptor.decrypt(group.getUpdateNm()));
            }
        }
        return ResponseEntity.ok().body(groupList);
    }

    /**
     * name : postGroup 
     * description : group save
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.GROUP_SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping("/group")
    @ResponseBody
    public ResponseEntity<?> postGroup(@RequestBody List<Swcatgrp> dataList, HttpServletRequest request)
            throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Map<String, Object> param = new HashMap<String, Object>();
        List<Swcat> swcatList = null;
        Swcatgrp swcatgrp = null;
        List<Swcatgrp> saveList = new ArrayList<Swcatgrp>();
        for (Swcatgrp data : dataList) {
            swcatgrp = new Swcatgrp();
            swcatgrp.setSwcatgrpId(data.getSwcatgrpId());
            swcatgrp.setSwcatId(data.getSwcatId());
            swcatgrp.setCreateId(userDetails.getUsername());
            swcatgrp.setUpdateId(userDetails.getUsername());
            saveList.add(swcatgrp);
        }
        return ResponseEntity.ok().body(swcatgrpRepository.saveAll(saveList));
    }

    /**
     * name : deleteGroup 
     * description : group delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + SwcatController.GROUP_DELETE + "')")
    @Transactional(value = "transactionManager")
    @DeleteMapping(value = "/group/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Long id) throws Exception {
        swcatgrpRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}