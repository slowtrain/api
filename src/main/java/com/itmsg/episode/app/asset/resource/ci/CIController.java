/**
* IT 자원 컨트롤러 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import com.itmsg.episode.app.asset.management.swcat.Swcat;
import com.itmsg.episode.app.asset.resource.cihis.CIhis;
import com.itmsg.episode.app.asset.resource.cisw.CIsw;
import com.itmsg.episode.app.asset.resource.cisw.CIswRepository;
import com.itmsg.episode.app.asset.resource.disci.DisCI;
import com.itmsg.episode.app.asset.resource.disci.DisCIMapToCI;
import com.itmsg.episode.app.asset.resource.disci.DisCIMapToCIRepository;
import com.itmsg.episode.app.asset.resource.disci.DisCIMapper;
import com.itmsg.episode.app.sidebar.UserCiRepository;
import com.itmsg.episode.app.asset.resource.ci.RparespmapRepository;
import com.itmsg.episode.app.system.classification.ClassstructureRepository;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.app.system.workflow.WFInstance;
import com.itmsg.episode.app.system.workflow.WorkflowService;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = CIController.URL)
public class CIController {
    public static final String APPLICATION = "CI";
    public static final String URL = "/api/asset/resource/ci";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI attachment option")
    public static final String ATTACHMENT = "CI_ATTACHMENT";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI read option")
    public static final String READ = "CI_READ";
    
    @ESApplicationOption(application = CIController.APPLICATION, description = "CI add option")
    public static final String ADD = "CI_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI save option")
    public static final String SAVE = "CI_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI delete option")
    public static final String DELETE = "CI_DELETE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI spec add option")
    public static final String SPEC_ADD = "CI_SPEC_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI spec save option")
    public static final String SPEC_SAVE = "CI_SPEC_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI spec delete option")
    public static final String SPEC_DELETE = "CI_SPEC_DELETE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI rpa add option")
    public static final String RPA_ADD = "CI_RPA_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI rpa save option")
    public static final String RPA_SAVE = "CI_RPA_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI rpa delete option")
    public static final String RPA_DELETE = "CI_RPA_DELETE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation add option")
    public static final String RELATION_ADD = "CI_RELATION_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation save option")
    public static final String RELATION_SAVE = "CI_RELATION_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation delete option")
    public static final String RELATION_DELETE = "CI_RELATION_DELETE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation map add option")
    public static final String RELATION_MAP_ADD = "CI_RELATION_MAP_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation map save option")
    public static final String RELATION_MAP_SAVE = "CI_RELATION_MAP_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI relation map delete option")
    public static final String RELATION_MAP_DELETE = "CI_RELATION_MAP_DELETE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI disci map add option")
    public static final String DISCI_MAP_ADD = "CI_DISCI_MAP_ADD";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI disci map save option")
    public static final String DISCI_MAP_SAVE = "CI_DISCI_MAP_SAVE";

    @ESApplicationOption(application = CIController.APPLICATION, description = "CI disci map delete option")
    public static final String DISCI_MAP_DELETE = "CI_DISCI_MAP_DELETE";
    
    @Resource
    private WorkflowService workflowService;
    
    @Resource
    private CIService ciService;

    @Resource
    private CIMapper ciMapper;

    @Resource
    private DisCIMapper disCIMapper;
    
    @Resource
    private CIRepository ciRepository;

    @Resource
    private CIspecRepository cispecRepository;

    @Resource
    private CIrelationRepository cirelationRepository;

    @Resource
    private CIrelationmapRepository cirelationmapRepository;

    @Resource
    private ClassstructureRepository classstructureRepository;

    @Resource
    private DisCIMapToCIRepository disCIMapToCIRepository;
    
    @Resource
    private UserRepository userRepository;
    
    @Resource
    private RparespmapRepository rparespmapRepository;

    @Resource
    private ItsmCIRepository itsmCIRepository;

    @Resource
    private ItsmCIspecRepository itsmCIspecRepository;

    @Resource
    private UserCiRepository userCiRepository;
    
    @Resource
    CIswRepository cIswRepository;
    
    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : ci list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
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
     
    /**
     * name : get
     * description : ci one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        

        CI ciData = ciMapper.list(param).get(0);

        if(!StringUtils.isNull(ciData.getManagerNm())){
            ciData.setManagerNm(textEncryptor.decrypt(ciData.getManagerNm()));
        }
        if(!StringUtils.isNull(ciData.getCreateNm())){
            ciData.setCreateNm(textEncryptor.decrypt(ciData.getCreateNm()));
        }
        if(!StringUtils.isNull(ciData.getUpdateNm())){
            ciData.setUpdateNm(textEncryptor.decrypt(ciData.getUpdateNm()));
        }
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        String lang = userRepository.findByUsername(userDetails.getUsername()).getLang();
        param.put("classstructureId", ci.get().getClassstructureId());
        param.put("lang", lang);   
        Map<String, Object> ciExtNmData = ciService.getCIExtNm(param);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data",  ciData);
        rtnMap.put("ciExtNmData", ciExtNmData); 

        WFInstance wfInstance = workflowService.getWFInstance("CI",ciData.getId());
        rtnMap.put("wfInstance",wfInstance);
        rtnMap.put("wfAccessible", (wfInstance == null) || workflowService.isAssigned(wfInstance.getId(), userDetails.getUsername()));

        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : id
     * description : ciid 를 id로
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/id")
    @ResponseBody
    public ResponseEntity<?> id(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(ciRepository.findByOrgIdAndCiId((String)param.get("orgId"), (String)param.get("ciId")).getId());
    }

    /**
     * name : ciId
     * description : ci 신규 생성 아이디
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/ciid")
    @ResponseBody
    public ResponseEntity<?> ciId(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(ciMapper.ciId(param));
    }

    /**
     * name : getExtNm
     * description : ext항목명 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/ciextnm")
    @ResponseBody
    public ResponseEntity<?> getExtNm(@RequestParam Map<String, Object> param){
        Map<String, Object> ciExtNmData = ciService.getCIExtNm(param);

        return ResponseEntity.ok().body(ciExtNmData);
    }

    /**
     * name : post
     * description : ci create
     * @param : CI data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody CI data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        List<CIspec> specList = data.getSpecList();
        CI ci = ciRepository.save(data);
        for(CIspec spec : specList){
            spec.setCiId(ci.getCiId());
            spec.setUpdateId(userDetails.getUsername());
            spec.setCreateId(userDetails.getUsername());            
            cispecRepository.save(spec);
        }   
        return ResponseEntity.ok().body(ci);
    }

    /**
     * name : put
     * description : ci modify
     * @param : CI data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody CI data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setUpdateId(userDetails.getUsername());
        CI oldCI = ((Optional<CI>)ciRepository.findById(data.getId())).get();
        ciService.makeCIHist(ciService.HIS_TYPE_CI, ciService.HIS_CRDTYPE_UPDATE, oldCI, data);
        CI ci = ciRepository.save(data);

        List<CIspec> specList = data.getSpecList();
        CIspec oldCIspec = null;      
        for(CIspec spec : specList){
            spec.setUpdateId(userDetails.getUsername());
            if(spec.getId() != null){
                oldCIspec = ((Optional<CIspec>)cispecRepository.findById(spec.getId())).get();
                ciService.makeCIHist(ciService.HIS_TYPE_SPEC, ciService.HIS_CRDTYPE_UPDATE, oldCIspec, spec);    
            }else{
                spec.setCreateId(userDetails.getUsername());            
            }
            cispecRepository.save(spec);
        }   

        List<Rparespmap> rparespmapList = data.getRparespmapList();
        for(Rparespmap rparespmap : rparespmapList) {
            rparespmap.setCreateId(userDetails.getUsername());
            rparespmap.setUpdateId(userDetails.getUsername());
            rparespmapRepository.save(rparespmap);
        }

        List<CIrelation> relationList = data.getRelationList();
        CIrelation oldCIrelation = null;
        for(CIrelation relation : relationList){
            if("ACTIVE".equals(relation.getStatus())){
                String date = "9999-12-31 00:00:00";
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                relation.setEndDt(dt.parse(date));
            }else{
                Date date = new Date();
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.US);
                relation.setEndDt(dt.parse(dt.format(date)));
            }
            relation.setUpdateId(userDetails.getUsername());
            if(relation.getId() != null){
                oldCIrelation = ((Optional<CIrelation>)cirelationRepository.findById(relation.getId())).get();
                ciService.makeCIHist(ciService.HIS_TYPE_RELATION, ciService.HIS_CRDTYPE_UPDATE, oldCIrelation, relation);
            }else{
                relation.setUserdefine(true);
                relation.setCreateId(userDetails.getUsername());    
            }
            cirelationRepository.save(relation);
        }

        List<CIsw> relationSwList = data.getRelationSwList();
        for (CIsw relationSw : relationSwList) {
            CIsw cisw = cIswRepository.findById(relationSw.getId()).get();
            cisw.setIslicnouse(relationSw.getIslicnouse());
            cisw.setLicnusecause(relationSw.getLicnusecause());
            cisw.setUpdateId(userDetails.getUsername());
            cIswRepository.save(cisw);
        }

        return ResponseEntity.ok().body(ci);
    }

    /**
     * name : delete
     * description : ci delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        CI oldCI = ((Optional<CI>)ciRepository.findById(id)).get();
        ciService.makeCIHist(ciService.HIS_TYPE_CI, ciService.HIS_CRDTYPE_DELETE, oldCI, oldCI);
        ciRepository.deleteById(id);        
        cispecRepository.deleteByCiId(oldCI.getCiId());        
        rparespmapRepository.deleteByCiId(oldCI.getCiId());
        userCiRepository.deleteByCiId(oldCI.getCiId());
        return ResponseEntity.ok().build();
    }

    /**
     * name : specList
     * description : spec list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/cispec")
    @ResponseBody
    public ResponseEntity<?> specList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(cispecRepository.findByOrgIdAndCiId(ci.get().getOrgId(), ci.get().getCiId()));
    }

    /**
     * name : specSrchList
     * description : spec 입력 팝업 정보 조회
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/cispecSrch")
    @ResponseBody
    public ResponseEntity<?> specSrchList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("classstructureId", ci.get().getClassstructureId());
        param.put("ciId", ci.get().getCiId());
        return ResponseEntity.ok().body(ciMapper.specSrchList(param));
    }

    /**
     * name : postCIspec
     * description : spec save
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.SPEC_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/cispec")
    @ResponseBody
    public ResponseEntity<?> postCIspec(@RequestBody List<CIspec> dataList, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        CIspec oldCIspec = null;      
        for(CIspec data : dataList){
            data.setUpdateId(userDetails.getUsername());
            if(data.getId() != null){
                oldCIspec = ((Optional<CIspec>)cispecRepository.findById(data.getId())).get();
                ciService.makeCIHist(ciService.HIS_TYPE_SPEC, ciService.HIS_CRDTYPE_UPDATE, oldCIspec, data);    
            }else{
                data.setCreateId(userDetails.getUsername());            
            }
            cispecRepository.save(data);
        }   
        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteCIspec
     * description : spec delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.SPEC_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/cispec/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCIspec(@PathVariable("id") Long id) throws Exception{
        CIspec oldCIspec = ((Optional<CIspec>)cispecRepository.findById(id)).get();
        ciService.makeCIHist(ciService.HIS_TYPE_SPEC, ciService.HIS_CRDTYPE_DELETE, oldCIspec, oldCIspec);
        cispecRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : relationList
     * description : relation list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/cirelation")
    @ResponseBody
    public ResponseEntity<?> relationList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());

        List<CIrelation> relationList = ciMapper.relationList(param);
        for(CIrelation row : relationList){            
            if(!StringUtils.isNull(row.getSourceManagerNm())){
                row.setSourceManagerNm(textEncryptor.decrypt(row.getSourceManagerNm()));
            }  
            if(!StringUtils.isNull(row.getTargetManagerNm())){
                row.setTargetManagerNm(textEncryptor.decrypt(row.getTargetManagerNm()));
            }    
        }
        return ResponseEntity.ok().body(relationList);
    }

    /**
     * name : relationTabList
     * description : detail > relation tab list 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/cirelationTab")
    @ResponseBody
    public ResponseEntity<?> relationTabList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());

        List<CI> relationTabList = ciMapper.relationTabList(param);
        return ResponseEntity.ok().body(relationTabList);
    }

    /**
     * name : relationTabList
     * description : detail > relation tab list 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/relationswlist")
    @ResponseBody
    public ResponseEntity<?> relationSwList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());

        List<Swcat> relationSwList = ciMapper.relationSwList(param);
        return ResponseEntity.ok().body(relationSwList);
    }

    /**
     * name : downtimeList
     * description : downtime tab list 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/downtimelist")
    @ResponseBody
    public ResponseEntity<?> downtimeList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ciId", ci.get().getCiId());

        List<Eventdur> downtimeList = ciMapper.downtimeList(param);
        return ResponseEntity.ok().body(downtimeList);
    }

    /**
     * name : postRelation
     * description : relation create
     * @param : List<CIrelation> dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.RELATION_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/cirelation")
    @ResponseBody
    public ResponseEntity<?> postRelation(@RequestBody List<CIrelation> dataList, HttpServletRequest request) throws Exception{   
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        CIrelation oldCIrelation = null;
        for(CIrelation data : dataList){
            if("ACTIVE".equals(data.getStatus())){
                String date = "9999-12-31 00:00:00";
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                data.setEndDt(dt.parse(date));
            }else{
                Date date = new Date();
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.US);
                data.setEndDt(dt.parse(dt.format(date)));
            }
            data.setUpdateId(userDetails.getUsername());
            if(data.getId() != null){
                oldCIrelation = ((Optional<CIrelation>)cirelationRepository.findById(data.getId())).get();
                ciService.makeCIHist(ciService.HIS_TYPE_RELATION, ciService.HIS_CRDTYPE_UPDATE, oldCIrelation, data);
            }else{
                data.setUserdefine(true);
                data.setCreateId(userDetails.getUsername());    
            }
            cirelationRepository.save(data);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteRelation
     * description : relation delete
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.RELATION_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/cirelation")
    @ResponseBody
    public ResponseEntity<?> deleteRelation(@RequestParam Map<String, Object> param) throws Exception{
        Long id = Long.parseLong((String)param.get("id"));
        String ciId = (String)param.get("ciId");
        CIrelation oldCIrelation = ((Optional<CIrelation>)cirelationRepository.findById(id)).get();
        oldCIrelation.setCiId(ciId);
        ciService.makeCIHist(ciService.HIS_TYPE_RELATION, ciService.HIS_CRDTYPE_DELETE, oldCIrelation, oldCIrelation);
        cirelationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : hisList
     * description : hist list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/cihis")
    @ResponseBody
    public ResponseEntity<?> hisList(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());

        List<CIhis> hisList = ciMapper.hisList(param);
        for(CIhis row : hisList){                        
            if(!StringUtils.isNull(row.getUpdateNm())){
                row.setUpdateNm(textEncryptor.decrypt(row.getUpdateNm()));
            }    
        }
        return ResponseEntity.ok().body(hisList);
    }

    /**
     * name : relationmapList
     * description : relationmap list
     * @param : 
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/cirelationmap")
    @ResponseBody
    public ResponseEntity<?> relationmapList(){
        return ResponseEntity.ok().body(ciMapper.relationmapList());
    }

    /**
     * name : relationmapCodeList
     * description : relationmapCodeList list
     * @param : 
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/relationmapCodeList")
    @ResponseBody
    public ResponseEntity<?> relationmapCodeList(@RequestParam Map<String, Object> param){
        return ResponseEntity.ok().body(ciMapper.relationmapCodeList(param));
    }
    
    /**
     * name : postRelationmap
     * description : relationmap create
     * @param : Map data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.RELATION_MAP_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/cirelationmap")
    @ResponseBody
    public ResponseEntity<?> postRelationmap(@RequestBody Map<String, Object> data, HttpServletRequest request) throws Exception{   
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.put("createId", userDetails.getUsername());
        data.put("updateId", userDetails.getUsername());
        ciService.makeRelationmap(data);
        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteRelationmap
     * description : relationmap delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.RELATION_MAP_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/cirelationmap/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRelationmap(@PathVariable("id") Long id) throws Exception{
        Optional<CIrelationmap> cirelationmap = cirelationmapRepository.findById(id);
        if(!cirelationmap.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        CIrelationmap relationmap = cirelationmap.get();
        cirelationmapRepository.deleteByOrgIdAndSourceclassAndTargetclassAndRelationname(relationmap.getOrgId(), relationmap.getTargetclass(), relationmap.getSourceclass(), relationmap.getRelationname());
        cirelationmapRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : discicodeList
     * description : disci code list
     * @param :
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/discicodelist")
    @ResponseBody
    public ResponseEntity<?> discicodeList(){
        Map<String, Object> rtnData = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", "datasrc");
        rtnData.put("datasrc", ciMapper.discicodeList(param));
        param.put("code", "discitype");
        rtnData.put("discitype", ciMapper.discicodeList(param));
        param.put("code", "disciclass");
        rtnData.put("disciclass", ciMapper.discicodeList(param));        
        return ResponseEntity.ok().body(rtnData);
    }    

    /**
     * name : discimaptociList
     * description : discimaptoci list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/discimaptocilist")
    @ResponseBody
    public ResponseEntity<?> discimaptociList(@RequestParam Map<String, Object> param, HttpServletRequest request){
        return ResponseEntity.ok().body(ciMapper.discimaptociList(param));
    }
  
    /**
     * name : postDiscimaptoci
     * description : postDiscimaptoci create
     * @param : DisCIMapToCI data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.DISCI_MAP_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/discimaptoci")
    @ResponseBody
    public ResponseEntity<?> postDiscimaptoci(@RequestBody DisCIMapToCI data, HttpServletRequest request) throws Exception{   
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        disCIMapToCIRepository.save(data);
        return ResponseEntity.ok().build();
    }

    /**
     * name : putDiscimaptoci
     * description : postDiscimaptoci modify
     * @param : DisCIMapToCI data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.DISCI_MAP_SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/discimaptoci")
    @ResponseBody
    public ResponseEntity<?> putDiscimaptoci(@RequestBody DisCIMapToCI data, HttpServletRequest request) throws Exception{   
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setUpdateId(userDetails.getUsername());
        disCIMapToCIRepository.save(data);
        return ResponseEntity.ok().build();
    }

    /**
     * name : chkExistsDisci
     * description : 삭제전 disci 매칭 정보인지 확인
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/chkExistsDisci")
    @ResponseBody
    public ResponseEntity<?> chkExistsDisci(@RequestParam Map<String, Object> param, HttpServletRequest request){
        return ResponseEntity.ok().body(ciMapper.chkExistsDisci(param));
    }

    /**
     * name : deleteDiscimaptoci
     * description : Discimaptoci 정보 삭제
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.DISCI_MAP_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/discimaptoci/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDiscimaptoci(@PathVariable("id") Long id) throws Exception{
        disCIMapToCIRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : itsmCIList
     * description : itsm CI list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/itsmcilist")
    @ResponseBody
    public ResponseEntity<?> itsmCIList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(ItsmCI.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        param.put("srchtype", "list");
        rtnMap.put("data", ciMapper.itsmCIList(param));
        param.put("srchtype", "count");
        rtnMap.put("total", ciMapper.itsmCIListTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

     /**
     * name : getItsmCI
     * description : itsm ci one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/itsmcidata")
    @ResponseBody
    public ResponseEntity<?> getItsmCI(@PathVariable("id") Long id, HttpServletRequest request){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        ItsmCI itsmCI = new ItsmCI();
        List<ItsmCIspec> specList = null;

        if(!StringUtils.isNull(ci.get().getExtaln18())){
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("kaiCiid", ci.get().getExtaln18());
            List<ItsmCI> itsmCIList = ciMapper.itsmCIList(param);
            if(itsmCIList.size() > 0){
                itsmCI = itsmCIList.get(0);
                if(!StringUtils.isNull(itsmCI.getCiId())){
                    specList = itsmCIspecRepository.findByCiId(itsmCI.getCiId());
                }
            }
        }           
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("itsmCI",  itsmCI);
        rtnMap.put("specList", specList);
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : itsmClassList
     * description : itsm Class list
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/itsmclasslist")
    @ResponseBody
    public ResponseEntity<?> itsmClassList(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(ciMapper.itsmClassList(param));
    }

    /**
     * name : itsmCIspec
     * description : itsmCI spec list
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/itsmcispec")
    @ResponseBody
    public ResponseEntity<?> itsmCIspec(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(itsmCIspecRepository.findByCiId((String)param.get("ciId")));
    }

    /**
     * name : rpaCode
     * description : rpaCode data
     * @param : 
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/rpacode")
    @ResponseBody
    public ResponseEntity<?> rpaCode(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("linebizId", ci.get().getCiId());
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("exetoolscode", ciMapper.exetoolscode(param));
        rtnMap.put("linebizstepcode", ciMapper.linebizstepcode(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : getRparespmap
     * description : rparespmap list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ CIController.READ +"')")
    @GetMapping(value="/{id}/rparespmap")
    @ResponseBody
    public ResponseEntity<?> getRparespmap(@PathVariable("id") Long id){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(rparespmapRepository.findByCiId(ci.get().getCiId()));
    }

    /**
     * name : deleteRparespmap
     * description : rparespmap delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CIController.RPA_DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/rparespmap/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRparespmap(@PathVariable("id") Long id) throws Exception{
        rparespmapRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }

    /**
     * name : disCIList
     * description : DISCI list
     * @param : Map param
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('" + CIController.READ + "')")
    @GetMapping(value="/{id}/discilist")
    @ResponseBody
    public ResponseEntity<?> disciList(@PathVariable("id") Long id) {
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ciId", ci.get().getCiId());
        List<DisCI> disciList = disCIMapper.list(param);
        return ResponseEntity.ok().body(disciList);
    }
}