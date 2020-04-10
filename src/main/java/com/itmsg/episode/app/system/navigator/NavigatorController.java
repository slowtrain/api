/**
* 메뉴 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.navigator;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = NavigatorController.URL)
public class NavigatorController {
    public static final String APPLICATION = "NAVIGATOR";
    public static final String URL = "/api/system/navigator";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator read option")
    public static final String READ = "NAVIGATOR_READ";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator add option")
    public static final String ADD = "NAVIGATOR_ADD";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator save option")
    public static final String SAVE = "NAVIGATOR_SAVE";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator delete option")
    public static final String DELETE = "NAVIGATOR_DELETE";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator app add option")
    public static final String APP_ADD = "NAVIGATOR_APP_ADD";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator app save option")
    public static final String APP_SAVE = "NAVIGATOR_APP_SAVE";

    @ESApplicationOption(application = NavigatorController.APPLICATION, description = "Navigator app delete option")
    public static final String APP_DELETE = "NAVIGATOR_APP_DELETE";

    @Value("${jwt.header}")
    private String tokenHeader;

    @Resource
    private NavigatorMapper navigatorMapper;

    @Resource
    private NavigatorRepository navigatorRepository;
    
    @Resource
    private NavAncestorRepository navAncestorRepository;

    @Resource
    private NavigatorService navigatorService;

    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : Navigator list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.NAVIGATOR_LIST)
    @PreAuthorize("hasAuthority('" + NavigatorController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        return ResponseEntity.ok().body(navigatorMapper.list(param));
    }

    /**
     * name : get
     * description : Navigator get
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        Optional<Navigator> navigator = navigatorRepository.findById(id);
        if(!navigator.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Navigator rtnData = navigator.get();
        String createNm = systemCommonService.getusername(rtnData.getCreateId());
        if(!StringUtils.isNull(createNm)){
            rtnData.setCreateNm(textEncryptor.decrypt(createNm));
        }
        String updateNm = systemCommonService.getusername(rtnData.getUpdateId());
        if(!StringUtils.isNull(updateNm)){
            rtnData.setUpdateNm(textEncryptor.decrypt(updateNm));
        }
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : post
     * description : Navigator create
     * @param : Navigator data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Navigator data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        navigatorService.insertAncestorData(data);
        return ResponseEntity.ok().body(navigatorRepository.save(data));
    }

    /**
     * name : put
     * description : Navigator update
     * @param :  Long id, Navigator data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Navigator data, HttpServletRequest request) throws Exception{
        Optional<Navigator> navigator = navigatorRepository.findById(id);
        if(!navigator.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Navigator target = navigator.get();
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        target.setStatus(data.getStatus());
        target.setHierarchypath(data.getHierarchypath());
        target.setParent(data.getParent());
        target.setDescription(data.getDescription());
        target.setOrderSeq(data.getOrderSeq());
        target.setNavigatoriconsvg(data.getNavigatoriconsvg());
        target.setUpdateId(userDetails.getUsername());
        navigatorService.updateAncestorData(target);
        Navigator rtnData = navigatorRepository.save(target);
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : delete
     * description : Navigator delete
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<Navigator> navigator=navigatorRepository.findById(id);        
        if(!navigator.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
        navigatorRepository.deleteById(id);
        navAncestorRepository.deleteByOrgIdAndNavigatorId(navigator.get().getOrgId(), navigator.get().getNavigatorId());
        return ResponseEntity.ok().build();
    }

    /**
     * name : listApp
     * description : Navigator list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.READ+"')")
    @GetMapping(value="/{id}/app")
    @ResponseBody
    public ResponseEntity<?> listApp(@PathVariable("id") Long id){        
        Optional<Navigator> navigator=navigatorRepository.findById(id);
        
        if(!navigator.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", (String)navigator.get().getOrgId());
        param.put("parent", (String)navigator.get().getNavigatorId());
        param.put("type", "APP");
        return ResponseEntity.ok().body(navigatorMapper.list(param));
    }

    /**
     * name : postApp
     * description : Navigator App create
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.APP_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/app")
    @ResponseBody
    public ResponseEntity<?> postApp(@RequestBody List<Navigator> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);        
        for (Navigator data : dataList) {
            data.setCreateId(userDetails.getUsername());
            data.setUpdateId(userDetails.getUsername());
            navigatorService.insertAncestorData(data);
            navigatorRepository.save(data);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteApp
     * description : Navigator App delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.APP_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="app/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteApp(@PathVariable("id") Long id) throws Exception{
        Optional<Navigator> navigator=navigatorRepository.findById(id);        
        if(!navigator.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
        navigatorRepository.deleteById(id);
        navAncestorRepository.deleteByOrgIdAndNavigatorId(navigator.get().getOrgId(), navigator.get().getNavigatorId());
        return ResponseEntity.ok().build();
    }

    /**
     * name : listAncestor
     * description : Navigator ancestor list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+NavigatorController.READ+"')")
    @GetMapping(value="/{id}/ancestor")
    @ResponseBody
    public ResponseEntity<?> listAncestor(@PathVariable("id") Long id){        
        Optional<Navigator> navigator=navigatorRepository.findById(id);
        
        if(!navigator.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(navAncestorRepository.findByOrgIdAndAncestor(navigator.get().getOrgId(), navigator.get().getNavigatorId()));
    }
        
    /**
     * name : listAppSearch
     * description : Navigator App dialog list
     * @param : Map<String, Object> param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.NAVIGATOR_LIST)
    @PreAuthorize("hasAuthority('" + NavigatorController.READ + "')")
    @GetMapping(value="/appSearch")
    @ResponseBody
    public ResponseEntity<?> listAppSearch(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        
        return ResponseEntity.ok().body(navigatorMapper.appSearchList(param));
    }
    
    /**
     * name : mainNavilist
     * description : menu list
     * @param : Map<String, Object> param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.NAVIGATOR_LIST)
    @GetMapping(value="/mainNaviList")
    @ResponseBody
    public ResponseEntity<?> mainNavilist(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        
        List<Map<String, Object>> mainNaviList = navigatorService.mainNaviList(param);
        List<Navigator> mainList = navigatorMapper.list(param);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();  

        rtnMap.put("mainNaviList", mainNaviList);
        rtnMap.put("mainList", mainList);
        return ResponseEntity.ok().body(rtnMap);
    }
}