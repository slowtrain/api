/**
* 어플리케이션 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.ESModule;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value= ApplicationController.URL)
public class ApplicationController {
    
    public static final String APPLICATION="APPLICATION";

    public static final String URL="/api/system/application";
    
    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application read option")
    public static final String READ="APPLICATION_READ";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Add option")
    public static final String ADD="APPLICATION_ADD";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application save option")
    public static final String SAVE="APPLICATION_SAVE";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application delete option")
    public static final String DELETE="APPLICATION_DELETE";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Module Add option")
    public static final String MODULE_ADD="APPLICATION_MODULE_ADD";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Module save option")
    public static final String MODULE_SAVE="APPLICATION_MODULE_SAVE";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Module delete option")
    public static final String MODULE_DELETE="APPLICATION_MODULE_DELETE";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Label Add option")
    public static final String LABEL_ADD="APPLICATION_LABEL_ADD";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Label Save option")
    public static final String LABEL_SAVE="APPLICATION_LABEL_SAVE";

    @ESApplicationOption(application = ApplicationController.APPLICATION, description = "Application Label delete option")
    public static final String LABEL_DELETE="APPLICATION_LABEL_DELETE";

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private AuthorityRepository authorityRepository;

    @Resource
    private AppModuleRepository appModuleRepository;

    @Resource
    private AppLabelRepository appLabelRepository;

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private ApplicationService applictionService;

    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private TextEncryptor textEncryptor;
    
    /**
     * name : list
     * description : application list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.APPLICATION_LIST)
    @PreAuthorize("hasAuthority('"+ApplicationController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));      
        List<Application> appList = applicationMapper.list(param);  
        for(Application app : appList){
            String createNm = app.getCreateNm();
            String updateNm = app.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                app.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                app.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        }
        return ResponseEntity.ok().body(appList);       
    }

    /**
     * name : get
     * description : application one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        Optional<Application> app=applicationRepository.findById(id);
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String createNm = systemCommonService.getusername(app.get().getCreateId());
        String updateNm = systemCommonService.getusername(app.get().getUpdateId());
        if(!StringUtils.isNull(createNm)){
            app.get().setCreateNm(textEncryptor.decrypt(createNm));
        }
        if(!StringUtils.isNull(updateNm)){
            app.get().setUpdateNm(textEncryptor.decrypt(updateNm));
        }
        return ResponseEntity.ok().body(app.get());
    }

    /**
     * name : post
     * description : application create
     * @param : Application data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Application data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        
        Application app=new Application();
        app.setAppId(data.getAppId());
        app.setDescription(data.getDescription());
        app.setUrl(data.getUrl());
        app.setStatus(data.getStatus());
        app.setCreateId(userDetails.getUsername());
        app.setUpdateId(userDetails.getUsername());

        for(AppLabel appLabel : data.getAppLabel()){
            if(appLabel.getId() == null){
                appLabel.setCreateId(userDetails.getUsername());
            }
            appLabel.setUpdateId(userDetails.getUsername());
            appLabel.setAppId(data.getAppId());
            appLabelRepository.save(appLabel);
        }
        
        return ResponseEntity.ok().body(applicationRepository.save(app));
    }

    /**
     * name : put
     * description : application update
     * @param : Long id, Application data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")                            
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Application data, HttpServletRequest request) throws Exception{
        Optional<Application> app=applicationRepository.findById(id);
        
        if(!app.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Application target=app.get();

        target.setStatus(data.getStatus());
        target.setDescription(data.getDescription());
        target.setUrl(data.getUrl());
        target.setUpdateId(userDetails.getUsername());
        
        Application application = applicationRepository.save(target);

        for(AppLabel appLabel : data.getAppLabel()){
            if(appLabel.getId() == null){
                appLabel.setCreateId(userDetails.getUsername());
            }
            appLabel.setUpdateId(userDetails.getUsername());
            appLabelRepository.save(appLabel);
        }
        return ResponseEntity.ok().body(application);
    }

    /**
     * name : delete
     * description : application delete
     * @param : Long id, Application data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<Application> app=applicationRepository.findById(id);
        
        if(!app.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
        applicationRepository.delete(app.get());        
        return ResponseEntity.ok().build();
    }

    /**
     * name : moduleList
     * description : application module List
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.READ+"')")
    @GetMapping(value="/{id}/module")
    @ResponseBody
    public ResponseEntity<?> moduleList(@PathVariable("id") Long id){
        Optional<Application> app=applicationRepository.findById(id);
        
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<AppModule> appModules = appModuleRepository.findByAppId(app.get().getAppId());
        List<AppModule> result = new ArrayList<AppModule>();
        
        for(AppModule appModule:appModules){
            ESModule esModule = ESModule.valueOf(appModule.getModule_id());
            
            appModule.setDescription(esModule.description());
            result.add(appModule);
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * name : postModule
     * description : application module create
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.MODULE_SAVE+"')")
    @PostMapping(value="/module")
    @ResponseBody
    public ResponseEntity<?> postModule(@RequestBody List<AppModule> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(AppModule data:dataList){            
            data.setCreateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(appModuleRepository.saveAll(dataList));
    }

    /**
     * name : deleteModule
     * description : application module delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.MODULE_DELETE+"')")
    @DeleteMapping(value="/module/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteModule(@PathVariable("id") Long id){
        appModuleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * name : labelList
     * description : application label list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.READ+"')")
    @GetMapping(value="/{id}/applabel")
    @ResponseBody
    public ResponseEntity<?> labelList(@PathVariable("id") Long id){
        Optional<Application> app=applicationRepository.findById(id);
        
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(appLabelRepository.findByAppId(app.get().getAppId()));
    }

    /**
     * name : postLabels
     * description : application label create
     * @param : List dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.LABEL_SAVE+"')")
    @PostMapping(value="/label")
    @ResponseBody
    public ResponseEntity<?> postLabels(@RequestBody List<AppLabel> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);    
        for(AppLabel data : dataList){
            if(data.getId() == null){
                data.setCreateId(userDetails.getUsername());
            }
            data.setUpdateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(appLabelRepository.saveAll(dataList));
    }

    /**
     * name : deleteLabels
     * description : application label delete
     * @param : List dataList
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ApplicationController.LABEL_DELETE+"')")
    @DeleteMapping(value="/label/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteLabels(@PathVariable("id") Long id){
        appLabelRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}