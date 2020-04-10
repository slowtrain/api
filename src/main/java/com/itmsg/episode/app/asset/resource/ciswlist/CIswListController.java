/**
* 소프트웨어 목록 컨트롤러 클래스
* @author   최영훈
* @since    2019.08.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.29   최영훈   최초 생성
*/

package com.itmsg.episode.app.asset.resource.ciswlist;

import com.itmsg.episode.app.asset.resource.cisw.CIsw;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = CIswListController.URL)
public class CIswListController {
    public static final String APPLICATION = "CISWLIST";
    public static final String URL = "/api/asset/resource/ciswlist";

    @ESApplicationOption(application = CIswListController.APPLICATION, description = "CISWLIST read option")
    public static final String READ = "CISWLIST_READ";

    @Resource
    private CIswListMapper ciswListMapper;

    /**
     * name : list description : software list
     * 
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.CISW_LIST)
    @PreAuthorize("hasAuthority('" + CIswListController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        if (param.get("orderBy") != null) {
            param.put("orderBy", StringUtils.changeColName(CIsw.class, (String) param.get("orderBy")));
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("data", ciswListMapper.list(param));
        rtnMap.put("total", ciswListMapper.listTot(param));
        return ResponseEntity.ok().body(rtnMap);
    }
}