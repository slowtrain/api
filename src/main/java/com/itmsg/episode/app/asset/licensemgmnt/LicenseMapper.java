/**
* 라이선스 SQL Map 클래스
* @author   최영훈
* @since    2019.09.16
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.16   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.management.swcat.Swcat;

import java.util.List;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface LicenseMapper {
    List<SwLicense> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
    String licenseId(Map<String, Object> param);
    List<Swcat> swcatList(Map<String, Object> param);
    List<Map> chkDupSwcat(Map<String, Object> param);
    List<SwLicense> relationList(Map<String, Object> param);
    List<Map<String,Object>> companyLicense();
    List<Map<String,Object>> swlictypeLicense();
}