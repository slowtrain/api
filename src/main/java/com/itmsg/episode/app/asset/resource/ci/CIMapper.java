/**
* IT 자원  SQL Map 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.management.swcat.Swcat;
import com.itmsg.episode.app.asset.resource.cihis.CIhis;
import com.itmsg.episode.app.asset.resource.disci.DisCIMapToCI;
import com.itmsg.episode.app.system.classification.Classspec;

import org.apache.ibatis.annotations.Mapper;
  
@Mapper
@Resource(name="sqlSessionFactory")
public interface CIMapper {
    List<CI> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
    String ciId(Map<String, Object> param);
    void delete(Map<String,Object> param);
    TreeMap<String, Object> ciExtNmData(Map<String, Object> param);
    List<Classspec> specSrchList(Map<String, Object> param);
    List<CIrelation> relationList(Map<String, Object> param);    
    List<CI> relationTabList(Map<String, Object> param);    
    List<Swcat> relationSwList(Map<String, Object> param);
    List<Eventdur> downtimeList(Map<String, Object> param);    
    void deleteRelation(Map<String,Object> param);
    List<CIhis> hisList(Map<String, Object> param);
    List<CIrelationmap> relationmapList();
    List<Map<String, String>> relationmapCodeList(Map<String, Object> param);
    List<Map<String, Object>> rpaList();
    List<Map<String, Object>> discicodeList(Map<String, Object> param);
    List<DisCIMapToCI> discimaptociList(Map<String, Object> param);
    int chkExistsDisci(Map<String, Object> param);
    List<ItsmCI> itsmCIList(Map<String, Object> param);
    int itsmCIListTot(Map<String, Object> param);
    List<ItsmClass> itsmClassList(Map<String, Object> param);
    List<Map> exetoolscode(Map<String, Object> param);
    List<Map> linebizstepcode(Map<String, Object> param);

    CI getRelCi(String ciId);
    List<Map<String, Object>> getItInfraSituation(String ancestor);
    int infraTot(String ancestor, String cTime);

    Map<String,Object> mgConfirm();
    Map<String,Object> swLink();

    String convert(String tableName, String columnName, String classstructureId, String originalValue);

    Map<String, Object> scanCI(String scancitype);

    String getClassstructureNm(String orgId, String classstructureId);
}