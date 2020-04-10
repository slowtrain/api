/**
* 업체 SQL Map 클래스
* @author   최영훈
* @since    2019.10.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.company;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@Resource(name="sqlSessionFactory")
public interface CompanyMapper {
    List<Company> list(Map<String, Object> param);  
    int listTot(Map<String, Object> param);
}