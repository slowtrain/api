/**
* 변환 SQL Map 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@Resource(name="sqlSessionFactory")
public interface ConversionMapper {
    List<Conversion> list(Map<String, Object> param);  
    int listTot(Map<String, Object> param);
    List<Converter> converterList(Map<String, Object> param);  
    int converterListTot(Map<String, Object> param);
    List<Converter> converterCodeList();
    List<Map<String, String>> convertedvalueList(Map<String, String> param);
    void convertCI(Map<String, String> param);
}