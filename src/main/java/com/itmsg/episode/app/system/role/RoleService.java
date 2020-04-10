/**
* 롤 서비스 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/  
package com.itmsg.episode.app.system.role;

import com.itmsg.episode.app.system.user.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleUserRepository roleUserRepository;

    @Resource
    private RoleGroupRepository roleGroupRepository;

    @Resource
    private RoleAuthRepository roleAuthRepository;

    @Resource
    private RoleFilterRepository roleFilterRepository;
    
    @Transactional(value="transactionManager")
    public List<User> users(List<RoleUser> userList) {

            String userData = "";
        if (!userList.isEmpty()) {
            StringBuffer users = new StringBuffer();
            userList.stream().forEach(row -> {
                users.append("'" + row.getUsername() + "',");
            });
            userData = users.substring(0, users.length() - 1);
        }

        Map<String, String> map = new HashMap<>();
        map.put("ROLEUSERS", userData);

        return roleMapper.users(map);

    }

    
    @Transactional(value="transactionManager")
    public void addRoleUser(UserDetails userDetails, Map<String,Object> data){
        
        String roleId = data.get("roleId").toString();
        
        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");

        rows.stream().forEach(row -> {
            Optional<RoleUser> app= Optional.ofNullable(roleUserRepository.findByRoleIdAndUsername(roleId, row.get("username").toString()));

            if(!app.isPresent()){
                RoleUser gui = new RoleUser();
                gui.setRoleId(roleId);
                gui.setUsername(row.get("username").toString());
                gui.setCreateId(userDetails.getUsername());
                gui.setUpdateId(userDetails.getUsername());

                roleUserRepository.save(gui);
            }
        });
    }
    

    @Transactional(value="transactionManager")
    public void setUserGroup(UserDetails userDetails, Map<String,Object> data){

        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
        String roleId = data.get("roleId").toString();

        rows.stream().forEach(row -> {
            RoleGroup group = new RoleGroup();
            group.setRoleId(roleId);
            group.setUsergroupId(row.get("usergroupId").toString());
            group.setCreateId(userDetails.getUsername());
            group.setUpdateId(userDetails.getUsername());

            roleGroupRepository.save(group);
        });
    }

    @Transactional(value="transactionManager")
    public void setRoleAuth(UserDetails userDetails, Map<String,Object> data){

        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");

        rows.stream().forEach(row -> {
            Optional<RoleAuth> app = Optional.ofNullable(roleAuthRepository.findByRoleIdAndAppIdAndAuthorityId(
                    data.get("roleId").toString(), row.get("appId").toString(), row.get("option").toString()));
            if (!app.isPresent()) {
                RoleAuth auth = new RoleAuth();
                auth.setRoleId(data.get("roleId").toString());
                auth.setAuthorityId(row.get("option").toString());
                auth.setAppId(row.get("appId").toString());
                auth.setCreateId(userDetails.getUsername());
                auth.setUpdateId(userDetails.getUsername());
                roleAuthRepository.save(auth);
            }
        });
    }
}
