/**
* 업체 컨트롤러 클래스
* @author   최영훈
* @since    2019.10.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.company;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = CompanyController.URL)
public class CompanyController {
    public static final String APPLICATION = "COMPANY";
    public static final String URL = "/api/asset/management/company";

    @ESApplicationOption(application = CompanyController.APPLICATION, description = "Company read option")
    public static final String READ = "COMPANY_READ";
    
    @ESApplicationOption(application = CompanyController.APPLICATION, description = "Company add option")
    public static final String ADD = "COMPANY_ADD";

    @ESApplicationOption(application = CompanyController.APPLICATION, description = "Company save option")
    public static final String SAVE = "COMPANY_SAVE";

    @ESApplicationOption(application = CompanyController.APPLICATION, description = "Company delete option")
    public static final String DELETE = "COMPANY_DELETE";

    @Resource
    private CompanyRepository companyRepository;
    
    @Resource
    private TextEncryptor textEncryptor;
    
    @Resource
    private SystemCommonService systemCommonService;
   
    @Resource
    private CompanyMapper companyMapper;

    /**
     * name : list
     * description : company list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.COMPANY_LIST)
    @PreAuthorize("hasAuthority('" + CompanyController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listCompany(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Company.class, (String) param.get("orderBy")));
        }       

        List<Company> companyList = companyMapper.list(param);
        for(Company company : companyList){
            if(!StringUtils.isNull(company.getCompanynum())){
                company.setCompanynum(textEncryptor.decrypt(company.getCompanynum()));
            }
            if(!StringUtils.isNull(company.getCompanytel())){
                company.setCompanytel(textEncryptor.decrypt(company.getCompanytel()));
            }
            if(!StringUtils.isNull(company.getOwnername())){
                company.setOwnername(textEncryptor.decrypt(company.getOwnername()));
            }
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", companyList);
        rtnMap.put("total", companyMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : get
     * description : company one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.COMPANY_LIST)
    @PreAuthorize("hasAuthority('"+ CompanyController.READ +"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        

        Company company = companyMapper.list(param).get(0);

        if(!StringUtils.isNull(company.getCompanynum())){
            company.setCompanynum(textEncryptor.decrypt(company.getCompanynum()));
        }
        if(!StringUtils.isNull(company.getCompanytel())){
            company.setCompanytel(textEncryptor.decrypt(company.getCompanytel()));
        }
        if(!StringUtils.isNull(company.getOwnername())){
            company.setOwnername(textEncryptor.decrypt(company.getOwnername()));
        }
        String createNm = company.getCreateNm();
        if(!StringUtils.isNull(createNm)){
            company.setCreateNm(textEncryptor.decrypt(createNm));
        }
        String updateNm = company.getUpdateNm();
        if(!StringUtils.isNull(updateNm)){
            company.setUpdateNm(textEncryptor.decrypt(updateNm));
        }         
        return ResponseEntity.ok().body(company);
    }

    /**
     * name : companyId
     * description : company new id
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CompanyController.READ + "')")
    @GetMapping(value="/companyId")
    @ResponseBody
    public ResponseEntity<?> companyId(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(systemCommonService.getAutonum((String)param.get("orgId"), "company", "company_id"));
    }
    
    /**
     * name : postCompany
     * description : company create
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CompanyController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> postCompany(@RequestBody Company data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        if(!StringUtils.isNull(data.getCompanynum())){
            data.setCompanynum(textEncryptor.encrypt(data.getCompanynum()));
        }
        if(!StringUtils.isNull(data.getCompanytel())){
            data.setCompanytel(textEncryptor.encrypt(data.getCompanytel()));
        }
        if(!StringUtils.isNull(data.getOwnername())){
            data.setOwnername(textEncryptor.encrypt(data.getOwnername()));
        }
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(companyRepository.save(data));
    }

    /**
     * name : putCompany
     * description : company modify
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CompanyController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> putCompany(@PathVariable("id") Long id, @RequestBody Company data, HttpServletRequest request) throws Exception{
        Optional<Company> company = companyRepository.findById(id);
        if(!company.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Company target = company.get();

        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);

        target.setCompanyname(data.getCompanyname());
        target.setCompanytype(data.getCompanytype());
        target.setCompanydescription(data.getCompanydescription());
        if(!StringUtils.isNull(data.getCompanynum())){
            target.setCompanynum(textEncryptor.encrypt(data.getCompanynum()));
        }
        if(!StringUtils.isNull(data.getCompanytel())){
            target.setCompanytel(textEncryptor.encrypt(data.getCompanytel()));
        }
        if(!StringUtils.isNull(data.getOwnername())){
            target.setOwnername(textEncryptor.encrypt(data.getOwnername()));
        }
        target.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(companyRepository.save(target));
    }

    /**
     * name : deleteCompany
     * description : company delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+CompanyController.DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCompany(@PathVariable("id") Long id) throws Exception{
        companyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}