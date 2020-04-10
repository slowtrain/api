/**
* Controller of TOPOLOGY FOR BUSINESSSYSTEM
* @author   백승우
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   백승우   최초 생성
*/

package com.itmsg.episode.app.topology;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = TopologyBusinessController.URL)
public class TopologyBusinessController {
    public static final String APPLICATION = "TOPOLOGYBUSINESS";
    public static final String URL = "/api/topologybusiness";

    @Resource
    private TopologyBusinessService topologyBusinessService;

    @Resource
    private TopologyBusinessMapper topologyBusinessMapper;

    @GetMapping(value = "/network")
    @ResponseBody
    public ResponseEntity<?> network(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        return ResponseEntity.ok().body(topologyBusinessService.getNetwork(param, request));
    }

}