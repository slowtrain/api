/**
* 코드체계 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.domain;

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
import com.itmsg.episode.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value= DomainController.URL)
public class DomainController {

    public static final String DOMAIN="DOMAIN";

    public static final String URL="/api/system/esdomain";

    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain read option")
    public static final String READ = "DOMAIN_READ";

    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain add option")
    public static final String ADD = "DOMAIN_ADD";
    
    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain save option")
    public static final String SAVE = "DOMAIN_SAVE";

    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain delete option")
    public static final String DELETE = "DOMAIN_DELETE";
    
    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain Detail add option")
    public static final String DETAIL_ADD = "DOMAIN_DETAIL_ADD";

    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain Detail save option")
    public static final String DETAIL_SAVE = "DOMAIN_DETAIL_SAVE";

    @ESApplicationOption(application = DomainController.DOMAIN, description = "Domain Detail delete option")
    public static final String DETAIL_DELETE = "DOMAIN_DETAIL_DELETE";

    @Resource
    private DomainRepository domainRepository;

    @Resource
    private ESDomainRepository esdomainRepository;

    @Resource
    private DomainMapper domainMapper;

    @Resource
    private DomainService domainService;

    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : domain list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.DOMAIN_LIST)
    @PreAuthorize("hasAuthority('"+DomainController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        List<ESDomain> esDomainList =  domainMapper.list(param);
        for(ESDomain esDomain : esDomainList){
            String createNm = esDomain.getCreateNm();
            String updateNm = esDomain.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                esDomain.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                esDomain.setUpdateNm(textEncryptor.decrypt(updateNm));
            }   
        }
        return ResponseEntity.ok().body(esDomainList);
    }

    /**
     * name : post
     * description : domain create
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.SAVE+"')")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody ESDomain data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        if(data.getId() == null){
            data.setCreateId(userDetails.getUsername());
        }            
        data.setUpdateId(userDetails.getUsername());

        for(Domain domain : data.getDomainList()){
            if(domain.getId() == null){
                domain.setCreateId(userDetails.getUsername());
            }
            domain.setUpdateId(userDetails.getUsername());
            domain.setDomainId(data.getDomainId());
            domainRepository.save(domain);
        }
        
        return ResponseEntity.ok().body(esdomainRepository.save(data));
    }
    
    /**
     * name : put
     * description : domain update
     * @param : Long id, Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")                            
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody ESDomain data, HttpServletRequest request) throws Exception{
        Optional<ESDomain> esDomain = esdomainRepository.findById(id);
        
        if(!esDomain.isPresent()){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        ESDomain target = esDomain.get();

        target.setDescription(data.getDescription());
        target.setUpdateId(userDetails.getUsername());
        
        ESDomain rtnEsDomain = esdomainRepository.save(target);

        for(Domain domain : data.getDomainList()){
            if(domain.getId() == null){
                domain.setCreateId(userDetails.getUsername());
            }
            domain.setUpdateId(userDetails.getUsername());
            domainRepository.save(domain);
        }
        return ResponseEntity.ok().body(rtnEsDomain);
    }

    /**
     * name : delete
     * description : domain delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.DELETE+"')")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{

        Optional<ESDomain> app=esdomainRepository.findById(id);
        
        if(!app.isPresent()){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        esdomainRepository.delete(app.get());
        
        return ResponseEntity.ok().build();
    }

    /**
     * name : domainlist
     * description : domain list
     * @param : Map<String,Object> param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.READ+"')")
    @GetMapping(value="/domain")
    @ResponseBody
    public ResponseEntity<?> domainlist(@RequestParam Map<String,Object> param, HttpServletRequest request){
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        return ResponseEntity.ok().body(domainMapper.domainlist(param));
    }

    /**
     * name : domainlist
     * description : domain list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.READ+"')")
    @GetMapping(value="/{id}/domain")
    @ResponseBody
    public ResponseEntity<?> domainlist(@PathVariable("id") Long id){
       
        Optional<ESDomain> app=esdomainRepository.findById(id);
        
        if(!app.isPresent()){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        String domainId = app.get().getDomainId();

        Map<String, Object> param = new HashMap<String, Object>();
        
        param.put("domainId", domainId);

        return ResponseEntity.ok().body(domainMapper.domainlist(param));
    }
    
    /**
     * name : domainpost
     * description : domain post
     * @param : List <Domain> dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.DETAIL_SAVE+"')")
    @PostMapping(value="/domain")
    @ResponseBody
    public ResponseEntity<?> domainpost(@RequestBody List <Domain> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(Domain data : dataList){
            if(data.getId() == null){
                data.setCreateId(userDetails.getUsername());
            }            
			data.setUpdateId(userDetails.getUsername());
        } 
        return ResponseEntity.ok().body(domainRepository.saveAll(dataList));
    }  

    /**
     * name : domaindelete
     * description : domain delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+DomainController.DETAIL_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/domain/{id}")
    @ResponseBody
    public ResponseEntity<?> domaindelete(@PathVariable("id") Long id) throws Exception{
        
        Optional<Domain> app=domainRepository.findById(id);
        
        if(!app.isPresent()){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        domainRepository.delete(app.get());
        
        return ResponseEntity.ok().build();
    }
}