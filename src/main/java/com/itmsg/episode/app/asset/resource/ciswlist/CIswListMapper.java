/**
* 소프트웨어 목록 SQL Map 클래스
* @author   최영훈
* @since    2019.08.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ciswlist;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.cisw.CIsw;

import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface CIswListMapper {
    List<CIsw> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
}