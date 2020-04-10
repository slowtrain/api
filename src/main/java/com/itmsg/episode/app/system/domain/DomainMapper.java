/**
* 코드체계  SQL Map 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface DomainMapper {
    /*
    List<ESDomain> list(Map<String,String> param);
    List<Domain> esdomainlist(Map<String,String> param);
    */
    List<ESDomain> list(Map<String,Object> param);
    List<Domain> domainlist(Map<String,Object> param);
}
