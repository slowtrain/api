/**
* 사이드바 서비스 클래스
* @author   이정호
* @since    2019.08.13
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.13   이정호   최초 생성
*/  
package com.itmsg.episode.app.sidebar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


import java.util.List;
import java.util.Map;

@Service
public class SidebarService {

    @Resource
    private UserCiRepository userCiRepository;

    

    @Transactional(value="transactionManager")
    public void addCi(Map<String,Object> data, String userName){
                
        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");

        rows.stream().forEach(row -> {
                UserCi userCi = new UserCi();
                userCi.setUsername(userName);
                userCi.setCiId(row.get("ciId").toString());
                userCi.setOrgId(row.get("orgId").toString());
                userCi.setCreateId(userName);
                userCiRepository.save(userCi);
        });

    }

}
