/**
* 소프트웨어 카탈로그 SQL Map 클래스
* @author   최영훈
* @since    2019.10.14
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.14   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.swcat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.licensemgmnt.SwLicense;
import com.itmsg.episode.app.asset.resource.ci.CI;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SwcatMapper {
    List<Swcat> list(Map<String, Object> param);  
    int listTot(Map<String, Object> param);
    String swcatId(Map<String, Object> param);
    List<Swcat> relationList(Map<String, Object> param);  
    List<CI> serverList(Map<String, Object> param);
    List<SwLicense> licenseList(Map<String, Object> param);
    List<Swcat> rootdata(Map<String, Object> param);  
    List<Swcat> childrendata(Map<String, Object> param);
    Map<String,Object> swcatData();
    List<Swcat> groupList(Map<String, Object> param);  
}

