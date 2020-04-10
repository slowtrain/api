package com.itmsg.episode.system;

import com.itmsg.episode.app.asset.location.Location;
import com.itmsg.episode.app.asset.location.LocationMapper;
import com.itmsg.episode.app.system.application.AppLabel;
import com.itmsg.episode.app.system.application.AppLabelRepository;
import com.itmsg.episode.app.system.application.AuthorityRepository;
import com.itmsg.episode.app.system.classification.ClassificationMapper;
import com.itmsg.episode.app.system.classification.Classstructure;
import com.itmsg.episode.app.system.domain.DomainRepository;
import com.itmsg.episode.app.system.navigator.Navigator;
import com.itmsg.episode.app.system.navigator.NavigatorMapper;
import com.itmsg.episode.app.system.organization.Organization;
import com.itmsg.episode.app.system.organization.OrganizationMapper;
import com.itmsg.episode.app.system.organization.OrganizationRepository;
import com.itmsg.episode.app.system.organization.Orgvariable;
import com.itmsg.episode.app.system.organization.OrgvariableRepository;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserMapper;
import com.itmsg.episode.app.system.workflow.WorkflowService;
import com.itmsg.episode.security.JwtUser;
import com.itmsg.episode.util.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value= SystemCommonController.URL)
public class SystemCommonController {
    
    public static final String URL="/api/system/common";
    
	@Resource
    private WorkflowService workflowService;
	
    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private AuthorityRepository authorityRepository;

    @Resource
    private DomainRepository domainRepository;

    @Resource
    private OrganizationRepository organizationRepository;

    @Resource
    private AppLabelRepository appLabelRepository;

    @Resource
    private UserMapper userMapper;
    
    @Resource
    private ClassificationMapper classificationMapper;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private LocationMapper locationMapper;

    @Resource
    private NavigatorMapper navigatorMapper;

    @Resource
    OrgvariableRepository orgvariableRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @GetMapping(value="/alive")
    @ResponseBody
    public ResponseEntity<?> alive(){
        return ResponseEntity.ok().build();
    }


    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/appoption")
    @ResponseBody
    public ResponseEntity<?> appOptionList(@RequestParam Map<String,String> param){
        //userAuthorityRepository.deleteById(id);
        return ResponseEntity.ok().body(systemCommonService.appOptionList(param.get("appId")));
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/datafilter")
    @ResponseBody
    public ResponseEntity<?> dataFilterList(){
        return ResponseEntity.ok().body(Arrays.asList(ESDataFilter.values()));
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/domain")
    @ResponseBody
    public ResponseEntity<?> domainList(@RequestParam Map<String,String> param){
        return ResponseEntity.ok().body(systemCommonService.getDomain(param.get("domainId")));
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/label")
    @ResponseBody
    public ResponseEntity<?> labels(@RequestParam Map<String,String> param){
        return ResponseEntity.ok().body(appLabelRepository.findAll());
    }

    

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/module")
    @ResponseBody
    public ResponseEntity<?> moduleList(@RequestParam Map<String,String> param){
        
        EnumSet<ESModule> forces = EnumSet.allOf(ESModule.class);
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();

        forces.forEach(esModule ->{
            Map<String,String> map = new HashMap<String,String>();
            map.put("module_id",esModule.name());
            map.put("description",esModule.description());
            result.add(map);
        });
        
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/user")
    @ResponseBody
    public ResponseEntity<?> user(@RequestParam Map<String,Object> param, HttpServletRequest request){
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
       
        List<User> userList = userMapper.list(param);

        userList.stream().forEach(row -> {
            
            if(row.getFirstname() != null && !row.getFirstname().equals("")) row.setFirstname(textEncryptor.decrypt(row.getFirstname()));
            if(row.getMobile() != null && !row.getMobile().equals("")) row.setMobile(textEncryptor.decrypt(row.getMobile()));
            if(row.getEmail() != null && !row.getEmail().equals(""))  row.setEmail(textEncryptor.decrypt(row.getEmail()));

            String createNm = row.getCreateNm();
            String updateNm = row.getUpdateNm();

            if(!StringUtils.isNull(createNm)){
                row.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                row.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        });

        return ResponseEntity.ok().body(userList);
    }

    @GetMapping(value="/classification")
    @ResponseBody
    public ResponseEntity<?> classification(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(classificationMapper.list(param));
    }

    @GetMapping(value="/classtree")
    @ResponseBody
    public ResponseEntity<?> classtree(@RequestParam Map<String, Object> param) {

        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();

        Map<String, Object> rootData = new HashMap<>();
        rootData.put("id", "root");
        rootData.put("label", "CLASSSTRUCTURE");

        Map<String, Object> rootInerData = new HashMap<>();            
        rootInerData.put("classstructureId", "CLASSSTRUCTURE");
        rootInerData.put("hierarchypath", "");
        rootData.put("data", rootInerData);

        List<Map<String, Object>> treeList = classificationMapper.treeList(param);
        
        Map<String, Object> node = treeList.get(0);
        
        Map<String, String> chgKeyMap = StringUtils.getFieldNames(Classstructure.class);
        List<Map<String, Object>> chgTreeList = new ArrayList<Map<String, Object>>();
        Map<String, Object> chgMap = null;
        for (Map<String, Object> tree : treeList) {
            chgMap = new HashMap<String, Object>();
            for (String key : chgKeyMap.keySet()){
                chgMap.put(chgKeyMap.get(key),tree.get(key));
            }
            chgTreeList.add(chgMap);
        }
        rootData.put("children", systemCommonService.makeTreeData(chgTreeList, null, "parent", "classstructureId", "description"));
        rtnList.add(rootData);
        return ResponseEntity.ok().body(rtnList);
    }

    @GetMapping(value="/organization")
    @ResponseBody
    public ResponseEntity<?> organization(@RequestParam Map<String,Object> param){
        List<Organization> organizationList = organizationMapper.list(param);
        return ResponseEntity.ok().body(organizationList);
    }

    @GetMapping(value="/orgvariable")
    @ResponseBody
    public ResponseEntity<?> getOrgvariableList(HttpServletRequest request){
        JwtUser userDetails = (JwtUser) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        String orgId = userDetails.getOrgId();
        List<Orgvariable> orgvariableList = orgvariableRepository.findByOrgId(orgId);
        return ResponseEntity.ok().body(orgvariableList);
    }

    @GetMapping(value="/location")
    @ResponseBody
    public ResponseEntity<?> location(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        List<Location> locations = locationMapper.list(param);  
        for(Location location : locations){
            String createNm = location.getCreateNm();
            String updateNm = location.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                location.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                location.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        }
   
        return ResponseEntity.ok().body(locations);
    }

    @GetMapping(value="/locationtree")
    @ResponseBody
    public ResponseEntity<?> locationtree(@RequestParam Map<String, Object> param) {

        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();

        Map<String, Object> rootData = new HashMap<>();
        rootData.put("id", "root");
        rootData.put("label", "LOCATION");

        Map<String, Object> rootInerData = new HashMap<>();            
        rootInerData.put("locationId", "LOCATION");
        rootData.put("data", rootInerData);

        List<Map<String, Object>> treeList = locationMapper.treeList(param);
        
        Map<String, Object> node = treeList.get(0);
        
        Map<String, String> chgKeyMap = StringUtils.getFieldNames(Location.class);

        List<Map<String, Object>> chgTreeList = new ArrayList<Map<String, Object>>();
        Map<String, Object> chgMap = null;
        for (Map<String, Object> tree : treeList) {
            chgMap = new HashMap<String, Object>();
            for (String key : chgKeyMap.keySet()){
                chgMap.put(chgKeyMap.get(key),tree.get(key));
            }
            chgTreeList.add(chgMap);
        }
        rootData.put("children", systemCommonService.makeTreeData(chgTreeList, null, "parent", "locationId", "description"));
        rtnList.add(rootData);
        return ResponseEntity.ok().body(rtnList);
    }

    @GetMapping(value="/navitree")
    @ResponseBody
    public ResponseEntity<?> navitree(@RequestParam Map<String, Object> param) {

        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();

        Map<String, Object> rootData = new HashMap<>();
        rootData.put("id", "root");
        rootData.put("label", "NAVIGATOR");

        Map<String, Object> rootInerData = new HashMap<>();            
        rootInerData.put("navigatorId", "NAVIGATOR");
        rootInerData.put("hierarchypath", "");
        rootData.put("data", rootInerData);

        List<Map<String, Object>> treeList = navigatorMapper.treeList(param);
        
        Map<String, Object> node = treeList.get(0);
        
        Map<String, String> chgKeyMap = StringUtils.getFieldNames(Navigator.class);
        List<Map<String, Object>> chgTreeList = new ArrayList<Map<String, Object>>();
        Map<String, Object> chgMap = null;
        for (Map<String, Object> tree : treeList) {
            chgMap = new HashMap<String, Object>();
            for (String key : chgKeyMap.keySet()){
                chgMap.put(chgKeyMap.get(key),tree.get(key));
            }
            chgTreeList.add(chgMap);
        }
        rootData.put("children", systemCommonService.makeTreeData(chgTreeList, null, "parent", "navigatorId", "name"));
        rtnList.add(rootData);
        return ResponseEntity.ok().body(rtnList);
    }
	
	    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/table")
    @ResponseBody
    public ResponseEntity<?> tableList(@RequestParam Map<String,String> param){
        return ResponseEntity.ok().body(systemCommonService.tableList(null));
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @PostMapping(value="/workflow/wfprogress")
    @ResponseBody
    public ResponseEntity<?> progress(@RequestBody Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("isOption", workflowService.progress(data, request));
        return ResponseEntity.ok().body(result);
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/workflow/wfprocess")
    @ResponseBody
    public ResponseEntity<?> wfprocess(@RequestParam("objectId") String objectId, HttpServletRequest request){        
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("wfprocesses", workflowService.getWFProcess(objectId,"ACTIVE"));
        return ResponseEntity.ok().body(result);
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/workflow/wfinstance")
    @ResponseBody
    public ResponseEntity<?> wfinstance(@RequestParam("objectId") String objectId, @RequestParam("uid") Integer uid, HttpServletRequest request){        
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("currentWFInstance", workflowService.getWFInstance(objectId, Long.valueOf(uid)));
        return ResponseEntity.ok().body(result);
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/workflow/wfassignment")
    @ResponseBody
    public ResponseEntity<?> wfassignment(HttpServletRequest request){
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        return ResponseEntity.ok().body(workflowService.getWFAssignment(userDetails.getUsername(), true));
    }

    //@PreAuthorize("hasAuthority('AUTHORITY_READ')")
    @GetMapping(value="/workflow/initialize")
    @ResponseBody
    public ResponseEntity<?> wfnode(@RequestParam("objectId") String objectId,@RequestParam("uid") Long uid, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        return ResponseEntity.ok().body(workflowService.initialize(objectId,uid,userDetails.getUsername()));
    }

}