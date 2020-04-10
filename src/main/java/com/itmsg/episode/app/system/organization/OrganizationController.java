/**
* 조직 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.organization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value= OrganizationController.URL)
public class OrganizationController {
    
    public static final String ORGANIZATION="ORGANIZATION";

    public static final String URL="/api/system/organization";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization read option")
    public static final String READ = "ORGANIZATION_READ";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization add option")
    public static final String ADD = "ORGANIZATION_ADD";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization save option")
    public static final String SAVE = "ORGANIZATION_SAVE";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization delete option")
    public static final String DELETE = "ORGANIZATION_DELETE";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization var add option")
    public static final String VAR_ADD = "ORGANIZATION_VAR_ADD";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization var save option")
    public static final String VAR_SAVE = "ORGANIZATION_VAR_SAVE";

    @ESApplicationOption(application = OrganizationController.ORGANIZATION, description = "Organization var delete option")
    public static final String VAR_DELETE = "ORGANIZATION_VAR_DELETE";
    
    @Value("${jwt.header}")
    private String tokenHeader;

    @Resource
    private OrganizationRepository organizationRepository;

    @Resource
    OrgvariableRepository orgvariableRepository;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private OrganizationService organizationnService;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private SystemCommonService systemCommonService;
    
    /**
     * name : list
     * description : Organization list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.ORGANIZATION_LIST)
    @PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));     
        List<Organization> organizationList = organizationMapper.list(param);
        for(Organization organization : organizationList){
            String createNm = organization.getCreateNm();
            String updateNm = organization.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                organization.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                organization.setUpdateNm(textEncryptor.decrypt(updateNm));
            }   
        }   
        return ResponseEntity.ok().body(organizationList);
    }

     /**
     * name : get
     * description : Organization one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){        
        Optional<Organization> org = organizationRepository.findById(id);        
        if(!org.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Organization organization = org.get();
        String createNm = systemCommonService.getusername(organization.getCreateId());
        String updateNm = systemCommonService.getusername(organization.getUpdateId());
        if(!StringUtils.isNull(createNm)){
            organization.setCreateNm(textEncryptor.decrypt(createNm));
        }
        if(!StringUtils.isNull(updateNm)){
            organization.setUpdateNm(textEncryptor.decrypt(updateNm));
        }   

        return ResponseEntity.ok().body(organization);
    }

    /**
     * name : post
     * description : Organization create
     * @param : Organization data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Organization data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
    	data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());    
        List<Orgvariable> variableList = data.getOrgvariable();
        for(Orgvariable variable : variableList) {
            variable.setCreateId(userDetails.getUsername());    
            variable.setUpdateId(userDetails.getUsername());    
        }
        Organization rtnData = organizationRepository.save(data);
        orgvariableRepository.saveAll(variableList);	
        return ResponseEntity.ok().body(organizationRepository.save(rtnData));
    }

    /**
     * name : post
     * description : Organization update
     * @param : Long id, Organization data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")                            
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Organization data, HttpServletRequest request) throws Exception{
        Optional<Organization> org = organizationRepository.findById(id);
        if(!org.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Organization target = org.get();
        
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        
        target.setDescription(data.getDescription());
        target.setStatus(data.getStatus());
        target.setUpdateId(userDetails.getUsername());

        List<Orgvariable> variableList = data.getOrgvariable();
        for(Orgvariable variable : variableList) {
            if(variable.getId() == null){
                variable.setCreateId(userDetails.getUsername());
            }
            variable.setUpdateId(userDetails.getUsername());    
        }

        data = organizationRepository.save(target);
        orgvariableRepository.saveAll(variableList);
        return ResponseEntity.ok().body(data);
    }

    /**
     * name : delete
     * description : Organization delete
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<Organization> org=organizationRepository.findById(id);
        if(!org.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        organizationRepository.delete(org.get());
        orgvariableRepository.deleteByOrgId(org.get().getOrgId());
        return ResponseEntity.ok().build();
    }

    /**
     * name : listOrgvariable
     * description : Orgvariable list
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/{id}/orgvariable")
    @ResponseBody
    public ResponseEntity<?> listOrgvariable(@PathVariable("id") Long id){
        Optional<Organization> org = organizationRepository.findById(id);
        if(!org.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Orgvariable> orgvariableList = orgvariableRepository.findByOrgId(org.get().getOrgId());
        return ResponseEntity.ok().body(orgvariableList);
    }

    /**
     * name : deleteOrgvariable
     * description : Orgvariable delete
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.VAR_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/orgvariable/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteOrgvariable(@PathVariable("id") Long id) throws Exception{
        orgvariableRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : getOrgvariableList
     * description : Orgvariable list
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/orgvariable")
    @ResponseBody
    public ResponseEntity<?> getOrgvariableList(@RequestParam("orgId") String orgId){
        Optional<Organization> org = organizationRepository.findByOrgId(orgId);
        if(!org.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Orgvariable> orgvariableList = orgvariableRepository.findByOrgId(org.get().getOrgId());
        return ResponseEntity.ok().body(orgvariableList);
    }
}