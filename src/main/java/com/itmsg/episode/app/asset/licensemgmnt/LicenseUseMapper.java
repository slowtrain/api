/**
* 라이선스사용 SQL Map 클래스
* @author   최영훈
* @since    2019.09.30
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.30   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.management.swcat.Swcat;
import com.itmsg.episode.app.asset.resource.ci.CI;

import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface LicenseUseMapper {
    List<Swcat> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
    List<CI> serverList(Map<String, Object> param);
}