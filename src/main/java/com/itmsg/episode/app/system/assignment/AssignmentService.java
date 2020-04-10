package com.itmsg.episode.app.system.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.app.system.usergroup.GroupUserRepository;
import com.itmsg.episode.app.system.usergroup.UserGroupService;
import com.itmsg.episode.system.SystemCommonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    SystemCommonService systemCommonService;

    @Resource
    JpaTransactionManager transactionManager;

    @Resource
    UserRepository userRepository;

    @Resource
    UserGroupService userGroupService;

    @Resource
    GroupUserRepository groupUserRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<String> getAssignments(String username, String assignmentId, String objectId, Long objectUid) {
        List<String> users = new ArrayList<String>();
        List<Assignment> assignList = assignmentRepository.findByAssignmentId(assignmentId);
        if (assignList.isEmpty()) {
            return users;
        }

        Assignment assignment = assignList.get(0);
        String type = assignment.getType();
        String target = assignment.getTarget();
        
        if ("USER".equals(type)) {
            String[] targets = target.split(",");
            for(String targetUser: targets){
                if (targetUser.equals("{{username}}")) {
                    users.add(username);
                } else if (targetUser.equals("{{supervisor}}")) {
                    users.addAll(userGroupService.getGroupChiefs(username));
                } else if (targetUser.startsWith("{{:") && targetUser.endsWith("}}")) {
                    String targetExtracted = targetUser.substring(targetUser.lastIndexOf("{{:")+3,targetUser.lastIndexOf("}}"));
                    Map<String,Object> data = systemCommonService.findOne(objectId, objectUid);              
                    users.add((String)data.get(targetExtracted));
                } else {
                    users.add(targetUser);
                }
            }
        } else if ("USERGROUP".equals(type)) {
            if (target.equals("{{usergroup}}")) {
                users = userGroupService.getGroupUserByUsername(username);
            } else if (target.startsWith("{{:") && target.endsWith("}}")) {
                String targetExtracted = target.substring(target.lastIndexOf("{{:")+3,target.lastIndexOf("}}"));
                Map<String,Object> data = systemCommonService.findOne(objectId, objectUid);
                users = userGroupService.getGroupUserByUsergroupId((String)data.get(targetExtracted));
            } else {
                users = userGroupService.getGroupUserByUsergroupId(target);
            }
        }

        return users;
    }

}