/**
* 유저 그룹 서비스 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserGroupService {
    
    @Resource
    private UserGroupMapper usergroupMapper;

    public List<String> getGroupChiefs(String username){
        return usergroupMapper.getGroupChiefs(username);
    }

    public List<String> getGroupUserByUsername(String username){
        return usergroupMapper.getGroupUserByUsername(username);
    }

    public List<String> getGroupUserByUsergroupId(String usergroupId){
        return usergroupMapper.getGroupUserByUsergroupId(usergroupId);
    }
}
