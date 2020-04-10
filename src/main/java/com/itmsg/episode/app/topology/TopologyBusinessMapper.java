/**
* Mapper of TOPOLOGY FOR BUSINESSSYSTEM
* @author   백승우
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   백승우   최초 생성
*/
package com.itmsg.episode.app.topology;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Mapper
@Resource(name="sqlSessionFactory")
public interface TopologyBusinessMapper {
    
    String getStartClass(Map<String,Object> param);
    Map<String,Object> getNode(Map<String,Object> param);
    List<Map<String,Object>> getAncestorLinks(Map<String,Object> param);
    List<Map<String,Object>> getAncestorNodes(Map<String,Object> param);
    List<Map<String,Object>> getChildrenLinks(Map<String,Object> param);
    List<Map<String,Object>> getChildrenNodes(Map<String,Object> param);
    List<Map<String,Object>> getEvent(Map<String,Object> param);

    List<Map<String,Object>> getEvents();
    
}