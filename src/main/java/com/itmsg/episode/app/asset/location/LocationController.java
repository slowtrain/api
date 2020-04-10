/**
* 위치 컨트롤러 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

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
@RequestMapping(value = LocationController.URL)
public class LocationController {
    public static final String APPLICATION = "LOCATION";
    public static final String URL = "/api/system/location";

    @ESApplicationOption(application = LocationController.APPLICATION, description = "Location read option")
    public static final String READ = "LOCATION_READ";

    @ESApplicationOption(application = LocationController.APPLICATION, description = "Location save option")
    public static final String SAVE = "LOCATION_SAVE";

    @ESApplicationOption(application = LocationController.APPLICATION, description = "Location add option")
    public static final String ADD = "LOCATION_ADD";

    @ESApplicationOption(application = LocationController.APPLICATION, description = "Location delete option")
    public static final String DELETE = "LOCATION_DELETE";

    @Resource
    private LocationMapper locationMapper;

    @Resource
    private LocationRepository locationRepository;

    @Resource
    private LocancestorRepository locancestorRepository;

    @Resource
    private LocationService locationService;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private SystemCommonService systemCommonService;

    /**
     * name : list
     * description : location list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.LOCATION_LIST)
    @PreAuthorize("hasAuthority('" + LocationController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {

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

    /**
     * name : get
     * description : location one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LocationController.READ + "')")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (!location.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String createNm = systemCommonService.getusername(location.get().getCreateId());
        String updateNm = systemCommonService.getusername(location.get().getUpdateId());
        if(!StringUtils.isNull(createNm)){
            location.get().setCreateNm(textEncryptor.decrypt(createNm));
        }
        if(!StringUtils.isNull(updateNm)){
            location.get().setUpdateNm(textEncryptor.decrypt(updateNm));
        }
    
        return ResponseEntity.ok().body(location.get());
    }

    /**
     * name : post
     * description : location create
     * @param : Location data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LocationController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Location data, HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());

        locationService.insertAncestorData(data);
        return ResponseEntity.ok().body(locationRepository.save(data));
    }

    /**
     * name : put
     * description : location modify
     * @param : Long id, Location data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LocationController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Location data, HttpServletRequest request) throws Exception {

        Optional<Location> location = locationRepository.findById(id);
        if(!location.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        Location target = location.get();

        target.setNamekor(data.getNamekor());
        target.setNameeng(data.getNameeng());
        target.setStatus(data.getStatus());
        target.setType(data.getType());
        target.setDescription(data.getDescription());
        target.setUpdateId(userDetails.getUsername());
        
        return ResponseEntity.ok().body(locationRepository.save(target));
    }

    /**
     * name : delete
     * description : location delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LocationController.DELETE + "')")
    @Transactional(value = "transactionManager")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        Optional<Location> location = locationRepository.findById(id);

        if (!location.isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        locationRepository.deleteById(id);
        locancestorRepository.deleteByOrgIdAndLocationId(location.get().getOrgId(), location.get().getLocationId());

        return ResponseEntity.ok().build();
    }

    /**
     * name : listAncestor
     * description : location ancestor list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + LocationController.READ + "')")
    @GetMapping(value = "/{id}/ancestor")
    @ResponseBody
    public ResponseEntity<?> listAncestor(@PathVariable("id") Long id) {
        Optional<Location> location = locationRepository.findById(id);

        if (!location.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(locancestorRepository.findByOrgIdAndAncestor(location.get().getOrgId(),
                location.get().getLocationId()));
    }
}