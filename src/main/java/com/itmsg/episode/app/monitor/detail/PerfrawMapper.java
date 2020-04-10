/**
* Rerfraw  SQL Map 클래스
* @author   이정호
* @since    2019.09.02
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.02   이정호   최초 생성
*/
package com.itmsg.episode.app.monitor.detail;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface PerfrawMapper {

    List<Map<String, Object>> getPerfType(Map<String, Object> param);
    Map<String, Object> serverPerf(Map<String, Object> param);
    Map<String, Object> serverPerf1(Map<String, Object> param);
    Map<String, Object> serverPerf2(Map<String, Object> param);
    List<Map<String, Object>> getDisk(Map<String, Object> param); 
    List<Map<String, Object>> multipleEmsData(Map<String, Object> param); 
    List<Map<String, Object>> relationData(Map<String, Object> param); 

    Map<String, Object> serverPerfAdd1(Map<String, Object> param);
    Map<String, Object> serverPerfAdd2(Map<String, Object> param);

    List<Map<String, Object>> multipleEmsAdd(Map<String, Object> param); 
    List<Map<String, Object>> relationAdd(Map<String, Object> param); 
    
}