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
public interface TopologyMapper {

    Map<String,Object> getNode(Map<String,Object> param);
    List<Map<String,Object>> getLinks(Map<String,Object> param);
    List<Map<String,Object>> getNodes(Map<String,Object> param);
	List<Map<String,Object>> getEvents(String convertNodesParam);
    
}