/**
* Rpa 서비스 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/  
package com.itmsg.episode.app.e2e;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Service
public class RparespService {

    @Resource
    RparespMapper rparespMapper;

     /**
     * name : getRparespList
     * description : RPA 버블차트에 대한 데이터 조회
     * @param : null
     * @return : ResponseEntity
     */      
    @Transactional(value="transactionManager")
    public Map<String,Object> getRparespList() {

        List<Map<String,Object>> rpaSystem = rparespMapper.getRpaSystem();
        List<Map<String,Object>> biz = rparespMapper.getBizList();
        Map<String, Object> bizDt = rparespMapper.getBizDt();
        
        Map<String,List<Map<String,Object>>> rpaData = new HashMap<>();
        rpaSystem.stream().forEach(row -> {
            
          String ciId = row.get("ciId").toString();

          List<Map<String,Object>> rpa = new ArrayList<>();

          biz.stream().forEach(bizRow -> {

            String bizId = bizRow.get("ciId").toString();

            rpa.add(rparespMapper.getLocationRpa(ciId, bizId));
           
          });
                       
        rpaData.put(ciId, rpa);

        });

        Map<String,Object> result = new HashMap<>();

        result.put("rpa", rpaSystem);
        result.put("biz", biz);
        result.put("bizDt", bizDt);
        result.put("rpaData", rpaData);

        return result;

    }

     /**
     * name : getRpaTopList
     * description : RPA 응답시간 TOP 10 조회
     * @param : null
     * @return : ResponseEntity
     */          
    @Transactional(value="transactionManager")
    public List<Map<String, Object>> getRpaTopList(String cTime) {

        List<Map<String, Object>> result  = rparespMapper.getRpaTopList(cTime);

        return result;

    }

     /**
     * name : getRpaData
     * description : E2E 조회 데이터
     * @param : Map param
     * @return : ResponseEntity
     */   
    @Transactional(value="transactionManager")
    public List<Map<String, Object>> getRpaData(Map<String, Object> param) {

      String rpa = param.get("rpa").toString();
      String [] rpaArr = rpa.split(",");
      param.put("rpaArr", rpaArr);

      List<Map<String, Object>> result = new ArrayList<>();
      for(String rpaId : rpaArr) {
          param.put("rpaId", rpaId);
          Map<String, Object> rpaData  = rparespMapper.rpaData(param);
          result.add(rpaData);
          param.remove("rpaId");
        }

        return result;

    }


}
