
package com.itmsg.episode.app.asset.resource.disci;

import com.itmsg.episode.app.system.classification.ClassstructureRepository;
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
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = DisCIController.URL)
public class DisCIController {
    public static final String APPLICATION = "DISCI";
    public static final String URL = "/api/asset/resource/disci";

    @ESApplicationOption(application = DisCIController.APPLICATION, description = "DisCI attachment option")
    public static final String ATTACHMENT = "DISCI_ATTACHMENT";

    @ESApplicationOption(application = DisCIController.APPLICATION, description = "DisCI read option")
    public static final String READ = "DISCI_READ";
    
    @ESApplicationOption(application = DisCIController.APPLICATION, description = "DisCI CI map save option")
    public static final String CI_MAP_SAVE = "DISCI_CI_MAP_SAVE";

    @Resource
    private DisCIService disCIService;

    @Resource
    private DisCIMapper disCIMapper;
    
    @Resource
    private DisCIRepository disCIRepository;

    @Resource
    private DisCIspecRepository disCIspecRepository;

    @Resource
    private DisCIrelationRepository disCIrelationRepository;

    @Resource
    private ClassstructureRepository classstructureRepository;

    @Resource
    private TextEncryptor textEncryptor;

    // page: disCI, get disCI list
    @DataFilterEnabled(filter = ESDataFilter.DISCI_LIST)
    @PreAuthorize("hasAuthority('" + DisCIController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(DisCI.class, (String) param.get("orderBy")));
        }       

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", disCIMapper.list(param));
        rtnMap.put("total", disCIMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    // page: DisCI, get DisCI info by id
    @DataFilterEnabled(filter = ESDataFilter.CI_LIST)
    @PreAuthorize("hasAuthority('"+ DisCIController.READ +"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){
        Optional<DisCI> disCI = disCIRepository.findById(id);
        if(!disCI.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("disciId", disCI.get().getDisciId());
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        

        DisCI disCIData = disCIMapper.list(param).get(0);
        return ResponseEntity.ok().body(disCIData);
    }

    // page: disci, put disci
    @PreAuthorize("hasAuthority('"+DisCIController.CI_MAP_SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody DisCI data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        DisCI target = ((Optional<DisCI>)disCIRepository.findById(id)).get();
        target.setCiId(data.getCiId());
        target.setIsupdate(true);;
        target.setUpdateId(userDetails.getUsername());
        DisCI rtnData = disCIRepository.save(target);
        return ResponseEntity.ok().body(rtnData);
    }

    // page: disCI, get disCIspec list
    @PreAuthorize("hasAuthority('"+ DisCIController.READ +"')")
    @GetMapping(value="/{id}/discispec")
    @ResponseBody
    public ResponseEntity<?> specList(@PathVariable("id") Long id){
        Optional<DisCI> disCI = disCIRepository.findById(id);
        if(!disCI.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<DisCIspec> disCIspecList = disCIspecRepository.findByDisciId(disCI.get().getDisciId());
        return ResponseEntity.ok().body(disCIspecList);
    }

    // page: disCI, get disCIrelation list
    @PreAuthorize("hasAuthority('"+ DisCIController.READ +"')")
    @GetMapping(value="/{id}/discirelation")
    @ResponseBody
    public ResponseEntity<?> discirelationList(@PathVariable("id") Long id){
        Optional<DisCI> disCI = disCIRepository.findById(id);
        if(!disCI.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("disciId", disCI.get().getDisciId());

        List<DisCIrelation> relationList = disCIMapper.discirelationList(param);
        return ResponseEntity.ok().body(relationList);
    }
}