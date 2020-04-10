/**
* 어플리케이션 손실비용 서비스 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.1.25   이정호   최초 생성
*/  
package com.itmsg.episode.app.asset.servicemgmnt.serviceapp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Service
public class ServiceAppService {

    @Resource
    private ServiceAppMapper serviceAppMapper;

     /**
     * name : getAppData
     * description : app 조회 데이터
     * @param : Map param
     * @return : ResponseEntity
     */   
    @Transactional(value="transactionManager")
    public List<Map<String, Object>> getAppData(Map<String, Object> param) {

      String app = param.get("app").toString();
      String [] appArr = app.split(",");
      param.put("appArr", appArr);

      List<Map<String, Object>> result = new ArrayList<>();
      for(String ciId : appArr) {
          param.put("ciId", ciId);
          Map<String, Object> appData  = serviceAppMapper.appData(param);
          result.add(appData);
          param.remove("ciId");
        }

        return result;

    }

}