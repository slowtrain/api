/**
* Controller of TOPOLOGY
* @author   백승우
* @since    2019.09.01
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.01   백승우   최초 생성
*/

package com.itmsg.episode.app.topology;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = TopologyController.URL)
public class TopologyController {
    public static final String APPLICATION = "TOPOLOGY";
    public static final String URL = "/api/topology";


    @Resource
    private TopologyMapper topologyMapper;

    @Resource
    private TopologyService topologyService;

    @GetMapping(value = "/node")
    @ResponseBody
    public ResponseEntity<?> node(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("node",topologyMapper.getNode(param));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/links")
    @ResponseBody
    public ResponseEntity<?> links(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put("convertedClasses", topologyService.convertClassParam(param));
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("links",topologyMapper.getLinks(param));
        result.put("nodes",topologyMapper.getNodes(param));
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/events")
    @ResponseBody
    public ResponseEntity<?> events(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("events",topologyMapper.getEvents(""));
        return ResponseEntity.ok().body(result);
    }

}