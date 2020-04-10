/**
* 서비스 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.04
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.04   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.servicemgmnt.service;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.app.asset.resource.ci.CIService;
import com.itmsg.episode.app.asset.resource.ci.CIrelation;
import com.itmsg.episode.app.asset.resource.ci.CIrelationRepository;
import com.itmsg.episode.app.asset.resource.ci.CIrelationmapRepository;
import com.itmsg.episode.app.asset.resource.ci.CIspec;
import com.itmsg.episode.app.asset.resource.ci.CIspecRepository;
import com.itmsg.episode.app.asset.resource.ci.Rparespmap;
import com.itmsg.episode.app.asset.resource.ci.RparespmapRepository;
import com.itmsg.episode.app.asset.resource.cihis.CIhis;
import com.itmsg.episode.app.system.classification.ClassstructureRepository;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;

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
@RequestMapping(value = ServiceController.URL)
public class ServiceController {
    public static final String APPLICATION = "SERVICE";
    public static final String URL = "/api/asset/servicemgmnt/service";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service attachment option")
    public static final String ATTACHMENT = "SERVICE_ATTACHMENT";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service read option")
    public static final String READ = "SERVICE_READ";
    
    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service add option")
    public static final String ADD = "SERVICE_ADD";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service save option")
    public static final String SAVE = "SERVICE_SAVE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service delete option")
    public static final String DELETE = "SERVICE_DELETE";
    
    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service spec add option")
    public static final String SPEC_ADD = "SERVICE_SPEC_ADD";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service spec save option")
    public static final String SPEC_SAVE = "SERVICE_SPEC_SAVE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service spec delete option")
    public static final String SPEC_DELETE = "SERVICE_SPEC_DELETE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service rpa add option")
    public static final String RPA_ADD = "SERVICE_RPA_ADD";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service rpa save option")
    public static final String RPA_SAVE = "SERVICE_RPA_SAVE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service rpa delete option")
    public static final String RPA_DELETE = "SERVICE_RPA_DELETE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service relation add option")
    public static final String RELATION_ADD = "SERVICE_RELATION_ADD";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service relation save option")
    public static final String RELATION_SAVE = "SERVICE_RELATION_SAVE";

    @ESApplicationOption(application = ServiceController.APPLICATION, description = "Service relation delete option")
    public static final String RELATION_DELETE = "SERVICE_RELATION_DELETE";
    
    @Resource
    private ServiceMapper serviceMapper;

    @Resource
    private CIService ciService;

    @Resource
    private CIMapper ciMapper;
    
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
    private UserRepository userRepository;
    
    @Resource
    private RparespmapRepository rparespmapRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private SystemCommonService systemCommonService;

    /**
     * name : list
     * description : service list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SERVICE_LIST)
    @PreAuthorize("hasAuthority('" + ServiceController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(CI.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", serviceMapper.list(param));
        rtnMap.put("total", serviceMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }
     
    /**
     * name : get
     * description : service one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.SERVICE_LIST)
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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

        CI ciData = serviceMapper.list(param).get(0);

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

        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : ciId
     * description : ci 신규 생성 아이디
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ServiceController.READ + "')")
    @GetMapping(value="/ciid")
    @ResponseBody
    public ResponseEntity<?> ciId(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(systemCommonService.getAutonum((String)param.get("orgId"), "ci", "ci_id"));
    }

    /**
     * name : getExtNm
     * description : ext항목명 조회
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
    @PreAuthorize("hasAuthority('"+ServiceController.SAVE+"')")
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
    @PreAuthorize("hasAuthority('"+ServiceController.SAVE+"')")
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
        return ResponseEntity.ok().body(ci);
    }

    /**
     * name : delete
     * description : ci delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ServiceController.DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        CI oldCI = ((Optional<CI>)ciRepository.findById(id)).get();
        ciService.makeCIHist(ciService.HIS_TYPE_CI, ciService.HIS_CRDTYPE_DELETE, oldCI, oldCI);
        ciRepository.deleteById(id);        
        cispecRepository.deleteByCiId(oldCI.getCiId());        
        rparespmapRepository.deleteByCiId(oldCI.getCiId());
        return ResponseEntity.ok().build();
    }

    /**
     * name : specList
     * description : spec list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
    @PreAuthorize("hasAuthority('"+ServiceController.SPEC_SAVE+"')")
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
    @PreAuthorize("hasAuthority('"+ServiceController.SPEC_DELETE+"')")
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
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
    @GetMapping(value="/{id}/cirelation")
    @ResponseBody
    public ResponseEntity<?> relationList(@PathVariable("id") Long id, @RequestParam Map<String, Object> param){
        Optional<CI> ci = ciRepository.findById(id);
        if(!ci.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        param.put("orgId", ci.get().getOrgId());
        param.put("ciId", ci.get().getCiId());

        List<CIrelation> relationList = serviceMapper.relationList(param);
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
     * name : postRelation
     * description : relation create
     * @param : List<CIrelation> dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ServiceController.RELATION_SAVE+"')")
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
    @PreAuthorize("hasAuthority('"+ServiceController.RELATION_DELETE+"')")
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
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
    @GetMapping(value="cirelationmap")
    @ResponseBody
    public ResponseEntity<?> relationmapList(){
        return ResponseEntity.ok().body(cirelationmapRepository.findAll());
    }

    /**
     * name : rpaCode
     * description : rpaCode data
     * @param : 
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
    @DataFilterEnabled(filter = ESDataFilter.SERVICE_LIST)
    @PreAuthorize("hasAuthority('"+ ServiceController.READ +"')")
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
     * name : postRparespmap
     * description : rparespmap create
     * @param : List data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ServiceController.RPA_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/rparespmap")
    @ResponseBody
    public ResponseEntity<?> postRparespmap(@RequestBody List<Rparespmap> dataList, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(Rparespmap data : dataList) {
            data.setCreateId(userDetails.getUsername());
            data.setUpdateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(rparespmapRepository.saveAll(dataList));
    }

    /**
     * name : deleteRparespmap
     * description : rparespmap delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ServiceController.RPA_DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/rparespmap/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRparespmap(@PathVariable("id") Long id) throws Exception{
        rparespmapRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }
}