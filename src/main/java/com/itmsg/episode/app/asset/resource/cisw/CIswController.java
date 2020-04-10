/**
* 소프트웨어 목록 컨트롤러 클래스
* @author   최영훈
* @since    2019.08.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.29   최영훈   최초 생성
*/

package com.itmsg.episode.app.asset.resource.cisw;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = CIswController.URL)
public class CIswController {
    public static final String APPLICATION = "CISW";
    public static final String URL = "/api/asset/resource/cisw";

    @ESApplicationOption(application = CIswController.APPLICATION, description = "CISW read option")
    public static final String READ = "CISW_READ";

    @ESApplicationOption(application = CIswController.APPLICATION, description = "CISW save option")
    public static final String SAVE = "CISW_SAVE";

    @Resource
    CIswRepository cIswRepository;

    @Resource
    private CIswMapper ciswMapper;

    /**
     * name : list description : software list
     * 
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CISW_LIST)
    @PreAuthorize("hasAuthority('" + CIswController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        if (param.get("orderBy") != null) {
            param.put("orderBy", StringUtils.changeColName(CIsw.class, (String) param.get("orderBy")));
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", ciswMapper.list(param));
        rtnMap.put("total", ciswMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : put description : software modify
     * 
     * @param : software data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + CIswController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping
    @ResponseBody
    public ResponseEntity<?> put(@RequestBody List<CIsw> dataList, HttpServletRequest request) throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        for (CIsw data : dataList) {
            CIsw cisw = cIswRepository.findById(data.getId()).get();
            cisw.setIslicnouse(data.getIslicnouse());
            cisw.setLicnusecause(data.getLicnusecause());
            cisw.setUpdateId(userDetails.getUsername());
            cIswRepository.save(cisw);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('" + CIswController.READ + "')")
    @GetMapping(value="/useserverlist")
    @ResponseBody
    public ResponseEntity<?> useServerList(@RequestParam Map<String, Object> param) {
        return ResponseEntity.ok().body(ciswMapper.useServerList(param));
    }
}