/**
* 분류 컨트롤러 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = ClassificationController.URL)
public class ClassificationController {
    public static final String APPLICATION = "CLASSIFICATION";
    public static final String URL = "/api/system/classification";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification read option")
    public static final String READ = "CLASSIFICATION_READ";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification add option")
    public static final String ADD = "CLASSIFICATION_ADD";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification save option")
    public static final String SAVE = "CLASSIFICATION_SAVE";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification delete option")
    public static final String DELETE = "CLASSIFICATION_DELETE";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification spec add option")
    public static final String SPEC_ADD = "CLASSIFICATION_SPEC_ADD";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification save option")
    public static final String SPEC_SAVE = "CLASSIFICATION_SPEC_SAVE";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification delete option")
    public static final String SPEC_DELETE = "CLASSIFICATION_SPEC_DELETE";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification usewith add option")
    public static final String USEWITH_ADD = "CLASSIFICATION_USEWITH_ADD";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification usewith save option")
    public static final String USEWITH_SAVE = "CLASSIFICATION_USEWITH_SAVE";

    @ESApplicationOption(application = ClassificationController.APPLICATION, description = "Classification usewith delete option")
    public static final String USEWITH_DELETE = "CLASSIFICATION_USEWITH_DELETE";


    @Value("${jwt.header}")
    private String tokenHeader;

    @Resource
    private ClassificationMapper classificationMapper;

    @Resource
    private ClassstructureRepository classstructureRepository;

    @Resource
    private ClassspecRepository classspecRepository;

    @Resource
    private ClassusewithRepository classusewithRepository;

    @Resource
    private ClassancestorRepository classancestorRepository;

    @Resource
    private ClassificationService classificationService;

    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : Classification list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CLASSIFICATION_LIST)
    @PreAuthorize("hasAuthority('" + ClassificationController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        
        return ResponseEntity.ok().body(classificationMapper.list(param));
    }

    /**
     * name : get
     * description : Classification one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        Optional<Classstructure> classstructure = classstructureRepository.findById(id);
        if(!classstructure.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        Classstructure rtnData = classstructure.get();

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
     * description : Classification create
     * @param : Classstructure data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Classstructure data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());        
        classificationService.insertAncestorData(data);
        Classstructure rtnData = classstructureRepository.save(data);

        List<Classspec> specDataList = data.getSpecDataList();
        if (specDataList.size() > 0) {
            for(Classspec spec : specDataList){
                if(spec.getId() == null){
                    spec.setCreateId(userDetails.getUsername());
                }
                spec.setUpdateId(userDetails.getUsername());
                spec.setClassstructureId(data.getClassstructureId());
            }
            classspecRepository.saveAll(specDataList);
        }

        List<Classusewith> usewithDataList = data.getUsewithDataList();
        if (usewithDataList.size() > 0) {
            for(Classusewith usewith : usewithDataList){
                usewith.setCreateId(userDetails.getUsername());
                usewith.setClassstructureId(data.getClassstructureId());
            }
            classusewithRepository.saveAll(usewithDataList);
        }
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : put
     * description : Classification update
     * @param :  Long id, Classstructure data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Classstructure data, HttpServletRequest request) throws Exception{
        Optional<Classstructure> classstructure = classstructureRepository.findById(id);
        if(!classstructure.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Classstructure target = classstructure.get();
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setId(target.getId());
        data.setClassstructureId(target.getClassstructureId());
        data.setOrgId(target.getOrgId());
        data.setDatasrc(target.getDatasrc());
        data.setCreateDt(target.getCreateDt());
        data.setCreateId(target.getCreateId());
        data.setUpdateId(userDetails.getUsername());
        classificationService.updateAncestorData(data);
        Classstructure rtnData = classstructureRepository.save(data);

        List<Classspec> specDataList = data.getSpecDataList();
        if (specDataList.size() > 0) {
            for(Classspec spec : specDataList){
                if(spec.getId() == null){
                    spec.setCreateId(userDetails.getUsername());
                }
                spec.setUpdateId(userDetails.getUsername());
            }
            classspecRepository.saveAll(specDataList);
        }

        List<Classusewith> usewithDataList = data.getUsewithDataList();
        if (usewithDataList.size() > 0) {
            for(Classusewith usewith : usewithDataList){
                usewith.setCreateId(userDetails.getUsername());
            }
            classusewithRepository.saveAll(usewithDataList);
        }
        return ResponseEntity.ok().body(rtnData);
    }

    /**
     * name : delete
     * description : Classification delete
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{

        Optional<Classstructure> classstructure=classstructureRepository.findById(id);
        
        if(!classstructure.isPresent()){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        classstructureRepository.deleteById(id);
        classancestorRepository.deleteByOrgIdAndClassstructureId(classstructure.get().getOrgId(), classstructure.get().getClassstructureId());

        classspecRepository.deleteByOrgIdAndClassstructureId(classstructure.get().getOrgId(), classstructure.get().getClassstructureId());

        return ResponseEntity.ok().build();
    }

    /**
     * name : listSpec
     * description : Classification spec list
     * @param :  Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.READ+"')")
    @GetMapping(value="/{id}/spec")
    @ResponseBody
    public ResponseEntity<?> listSpec(@PathVariable("id") Long id){        
        Optional<Classstructure> classstructure=classstructureRepository.findById(id);
        
        if(!classstructure.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(classspecRepository.findByOrgIdAndClassstructureId(classstructure.get().getOrgId(), classstructure.get().getClassstructureId()));
    }

    /**
     * name : saveSpec
     * description : Classification spec save
     * @param :  List<Classspec> dataList, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.SPEC_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/spec")
    @ResponseBody
    public ResponseEntity<?> saveSpec(@RequestBody List<Classspec> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(Classspec data : dataList){
            if(data.getId()==null){
                data.setCreateId(userDetails.getUsername());
            }
            data.setUpdateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(classspecRepository.saveAll(dataList));
    }

    /**
     * name : deleteSpec
     * description : Classification spec delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.SPEC_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/spec/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSpec(@PathVariable("id") Long id) throws Exception{

        classspecRepository.deleteById(id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * name : listUsewith
     * description : Classification usewith list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.READ+"')")
    @GetMapping(value="/{id}/usewith")
    @ResponseBody
    public ResponseEntity<?> listUsewith(@PathVariable("id") Long id){        
        Optional<Classstructure> classstructure=classstructureRepository.findById(id);
        
        if(!classstructure.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(classusewithRepository.findByOrgIdAndClassstructureId(classstructure.get().getOrgId(), classstructure.get().getClassstructureId()));
    }

    /**
     * name : saveUsewith
     * description : Classification usewith save
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.USEWITH_SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping(value="/usewith")
    @ResponseBody
    public ResponseEntity<?> saveUsewith(@RequestBody List<Classusewith> dataList, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(Classusewith data : dataList){
            data.setCreateId(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(classusewithRepository.saveAll(dataList));
    }

    /**
     * name : deleteUsewith
     * description : Classification usewith delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.USEWITH_DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/usewith/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUsewith(@PathVariable("id") Long id) throws Exception{

        classusewithRepository.deleteById(id);
        
        return ResponseEntity.ok().build();
    }

    /**
     * name : listAncestor
     * description : Classification ancestor list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.READ+"')")
    @GetMapping(value="/{id}/ancestor")
    @ResponseBody
    public ResponseEntity<?> listAncestor(@PathVariable("id") Long id){        
        Optional<Classstructure> classstructure=classstructureRepository.findById(id);
        
        if(!classstructure.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(classancestorRepository.findByOrgIdAndAncestor(classstructure.get().getOrgId(), classstructure.get().getClassstructureId()));
    }

    /**
     * name : classusewithList
     * description : Classification classusewith list
     * @param : String object
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ClassificationController.READ+"')")
    @GetMapping(value="/classusewith")
    @ResponseBody
    public ResponseEntity<?> classusewithList(@RequestParam("object") String object){      
        return ResponseEntity.ok().body(classusewithRepository.findByObject(object));
    }    
}