/**
* 변환 컨트롤러 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import com.itmsg.episode.app.system.application.AppLabelRepository;
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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = ConversionController.URL)
public class ConversionController {
    public static final String APPLICATION = "CONVERSION";
    public static final String URL = "/api/asset/management/conversion";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION read option")
    public static final String READ = "CONVERSION_READ";
    
    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION add option")
    public static final String ADD = "CONVERSION_ADD";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION save option")
    public static final String SAVE = "CONVERSION_SAVE";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION delete option")
    public static final String DELETE = "CONVERSION_DELETE";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERTER read option")
    public static final String CONVERTER_READ = "CONVERSION_CONVERTER_READ";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION CONVERTER add option")
    public static final String CONVERTER_ADD = "CONVERSION_CONVERTER_ADD";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION CONVERTER save option")
    public static final String CONVERTER_SAVE = "CONVERSION_CONVERTER_SAVE";

    @ESApplicationOption(application = ConversionController.APPLICATION, description = "CONVERSION CONVERTER delete option")
    public static final String CONVERTER_DELETE = "CONVERSION_CONVERTER_DELETE";
    
    @Resource
    private ConversionRepository conversionRepository;
    
    @Resource
    private ConverterRepository converterRepository;

    @Resource
    private ConversionsService conversionsService;

    @Resource
    private ConversionMapper conversionMapper;
   
    @Resource
    private TextEncryptor textEncryptor;
    
    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private AppLabelRepository appLabelRepository;

    /**
     * name : list
     * description : conversion list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CONVERSION_LIST)
    @PreAuthorize("hasAuthority('" + ConversionController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Conversion.class, (String) param.get("orderBy")));
        }       

        List<Conversion> conversionList = conversionMapper.list(param);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", conversionList);
        rtnMap.put("total", conversionMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : get
     * description : conversion one row
     * @param : Long id, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CONVERSION_LIST)
    @PreAuthorize("hasAuthority('"+ ConversionController.READ +"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        

        Conversion conversion = conversionMapper.list(param).get(0);
        String createNm = conversion.getCreateNm();
        if(!StringUtils.isNull(createNm)){
            conversion.setCreateNm(textEncryptor.decrypt(createNm));
        }
        String updateNm = conversion.getUpdateNm();
        if(!StringUtils.isNull(updateNm)){
            conversion.setUpdateNm(textEncryptor.decrypt(updateNm));
        }         
        return ResponseEntity.ok().body(conversion);
    }

    /**
     * name : post
     * description : conversion create
     * @param : Conversion data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ConversionController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Conversion data, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        conversionsService.conversion(null, data);
        return ResponseEntity.ok().body(conversionRepository.save(data));
    }

    /**
     * name : put
     * description : conversion modify
     * @param : Conversion data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ConversionController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Conversion data, HttpServletRequest request) throws Exception{
        Optional<Conversion> conversion = conversionRepository.findById(id);
        if(!conversion.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Conversion target = conversion.get();
        Conversion oldData = (Conversion)target.clone();
        
        target.setConverterId(data.getConverterId());
        target.setConversionprocess(data.getConversionprocess());
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        target.setUpdateId(userDetails.getUsername());
        conversionRepository.save(target);        

        conversionsService.conversion(oldData, data);
        return ResponseEntity.ok().build();
    }

    /**
     * name : delete
     * description : conversion delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ConversionController.DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        conversionRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }

    /**
     * name : converterList
     * description : converter list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CONVERSION_LIST)
    @PreAuthorize("hasAuthority('" + ConversionController.READ + "')")
    @GetMapping(value="/converter")
    @ResponseBody
    public ResponseEntity<?> converterList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));        
        
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(Conversion.class, (String) param.get("orderBy")));
        }       

        List<Converter> conversionList = conversionMapper.converterList(param);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", conversionList);
        rtnMap.put("total", conversionMapper.converterListTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : converter
     * description : converter one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ConversionController.READ + "')")
    @GetMapping(value="/converter/{id}")
    @ResponseBody
    public ResponseEntity<?> converter(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(converterRepository.findById(id));
    }

    /**
     * name : delete
     * description : conversion delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ConversionController.CONVERTER_DELETE+"')") 
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/converter/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteConverter(@PathVariable("id") Long id) throws Exception{
        converterRepository.deleteById(id);        
        return ResponseEntity.ok().build();
    }
    
    /**
     * name : postConverter
     * description : Converter create
     * @param : Conversion data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ConversionController.CONVERTER_SAVE+"')")
    @Transactional(value="transactionManager", propagation = Propagation.NOT_SUPPORTED)
    @PostMapping(value="/converter")
    @ResponseBody
    public ResponseEntity<?> postConverter(@RequestBody List<Converter> dataList, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for(Converter data : dataList){
            data.setUpdateId(userDetails.getUsername());
            if(data.getId() == null){
                data.setCreateId(userDetails.getUsername());
            }    
        }
        List<Converter> rtnList = converterRepository.saveAll(dataList);
        conversionsService.conversion(dataList);
        return ResponseEntity.ok().body(rtnList);
    }

    /**
     * name : converterCodeList
     * description : converter code list
     * @param : n/a
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ConversionController.READ + "')")
    @GetMapping(value="/convertercodelist")
    @ResponseBody
    public ResponseEntity<?> converterCodeList() {
        return ResponseEntity.ok().body(conversionMapper.converterCodeList());
    }

    /**
     * name : processList
     * description : process code list
     * @param : n/a
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + ConversionController.READ + "')")
    @GetMapping(value="/processlist")
    @ResponseBody
    public ResponseEntity<?> processList() {
        return ResponseEntity.ok().body(conversionsService.processList());
    }
}