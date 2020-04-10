/**
* Service of TOPOLOGY FOR BUSINESSSYSTEM
* @author   백승우
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   백승우   최초 생성
*/
package com.itmsg.episode.app.topology;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopologyBusinessService {
    
    @Resource
    private TopologyBusinessMapper topologyBusinessMapper;

    private int[] SWIM_LANE_NUMBERS = {50, 150, 250, 350};

    private Map<Integer,Map<String,Map<String,Object>>> swimLanesMap;
    private Map<String,Map<String,Object>> totalNodes;
    private Map<String,Map<String,Object>> totalLinks;

    public Map<String,Object> getNetwork(Map<String, Object> param, HttpServletRequest request){
        
        swimLanesMap = new HashMap<Integer,Map<String,Map<String,Object>>>();
        totalNodes = new HashMap<String,Map<String,Object>>();
        totalLinks = new HashMap<String,Map<String,Object>>();
        
        for(int swimLane:SWIM_LANE_NUMBERS){
            swimLanesMap.put(swimLane, new HashMap<String,Map<String,Object>>());
        }
        param.put("depth",0);
        param.put("startClass",topologyBusinessMapper.getStartClass(param));
        Map<String,Object> standardNode = topologyBusinessMapper.getNode(param);
        net(standardNode,"ANCESTOR");
        net(standardNode,"CHILDREN");

        for(int swimLane:SWIM_LANE_NUMBERS){
            totalNodes.putAll(swimLanesMap.get(swimLane));
        }

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("standardNode", standardNode);
        result.put("nodes", new ArrayList<Map<String,Object>>(totalNodes.values()));
        result.put("links",new ArrayList<Map<String,Object>>(totalLinks.values()));
        return result;
    }

    private void net(Map<String, Object> node, String direction) {
        Integer swimLaneNumber = getOffsetY(node);
        Map<String,Map<String,Object>> swimLane = swimLanesMap.get(swimLaneNumber);
        if(!swimLane.containsKey(node.get("id"))){
            node.put("annotations", getAnnotations(node));
            node.put("offsetY", swimLaneNumber );
            node.put("offsetX", (swimLane.size()+1)*100);
            List<Map<String,Object>> event = topologyBusinessMapper.getEvent(node);
            node.put("shape", getShape(node,event.isEmpty() ? null : (String)event.get(0).get("_color")));
            node.put("event", event);
            swimLane.put((String)node.get("id"),node);
        }

        node.put("depth",((Integer)node.get("depth")) + 1);
        List<Map<String,Object>> links = direction.equals("ANCESTOR") ? topologyBusinessMapper.getAncestorLinks(node) : topologyBusinessMapper.getChildrenLinks(node);
        for(Map<String,Object> link:links){
            if(!totalLinks.containsKey((String)link.get("id"))){
                totalLinks.put((String)link.get("id"), link);
            }
        }
        
        List<Map<String,Object>> children = direction.equals("ANCESTOR") ? topologyBusinessMapper.getAncestorNodes(node) : topologyBusinessMapper.getChildrenNodes(node);
        for(Map<String,Object> child : children){
            Integer childSwimLaneNumber = getOffsetY(child);
            Map<String,Map<String,Object>> childSwimLane = swimLanesMap.get(childSwimLaneNumber);
            if(!childSwimLane.containsKey((String)child.get("id"))){
                net(child, direction);
            }
        }   
    }

    private Integer getOffsetY(Map<String, Object> node) {
        String ancestor = (String)node.get("ancestor");
        if("BUSINESSSYSTEM".equals(ancestor)){
            return 50;
        }else if("GROUP_1".equals(ancestor)){
            return 150;
        }else if("SOFTWARE".equals(ancestor)||"DATAINTERFACE".equals(ancestor)){
            return 250;
        }else {
            return 350;
        }
    }

    private Map<String, Object> getShape(Map<String, Object> node,String eventColor) {
        // String color = "#4dbd74";
        String color = "#5C90DF";
        if((Integer)node.get("depth") == 0){
            color = "#6f42c1";
        }
        if(eventColor!=null){
            color = eventColor;
        }

        Map<String,Object> shape = new HashMap<String,Object>();
        shape.put("type", "Native");
        shape.put("content", ((String)node.get("content")).replaceAll("<svg","<g").replaceAll("</svg>","</g>").replaceFirst("<path ", "<path fill=\""+color+"\" "));
        node.put("content", null);
        return shape;
    }

    private List<Map<String, Object>> getAnnotations(Map<String, Object> node) {
        Map<String,Object> annotations = new HashMap<String,Object>();
        annotations.put("content",node.get("ciname"));
        annotations.put("margin", getMargin(node));
        return Arrays.asList(annotations);
    }

    private Map<String,Object> getMargin(Map<String,Object> node) {
        Map<String,Object> margin = new HashMap<String,Object>();
        margin.put("left",50);
        margin.put("bottom",15);
        return margin;
    }

}
