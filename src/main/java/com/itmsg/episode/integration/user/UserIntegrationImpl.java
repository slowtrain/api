package com.itmsg.episode.integration.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;

import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.app.system.usergroup.GroupUser;
import com.itmsg.episode.app.system.usergroup.GroupUserRepository;
import com.itmsg.episode.app.system.usergroup.UserGroup;
import com.itmsg.episode.app.system.usergroup.UserGroupRepository;
import com.itmsg.episode.integration.Integration;
import com.itmsg.episode.integration.IntegrationConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@WebService(targetNamespace = UserIntegrationEndpoint.NAMESPACE_URI, portName = UserIntegrationEndpoint.PORT_NAME, serviceName = UserIntegrationEndpoint.SERVICE_NAME, endpointInterface = "com.itmsg.episode.integration.user.UserIntegration")
public class UserIntegrationImpl implements UserIntegration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private GroupUserRepository groupUserRepository;

    @Resource
    private IFUserRepository ifUserRepository;

    @Resource
    private TextEncryptor textEncryptor;

    Map<String,IntegrationConsumer<Integration>> consumers;

    @Override
    public void syncUser(List<Integration> datas) {
        logger.debug("UserIntegration syncUser start");

        for(Integration data:datas){
            data = data.integrate(consumers.get(data.getActionFlag()));
            log(data);
        }
    }

    @PostConstruct
    private void initConsumer(){
        IntegrationConsumer<Integration> createConsumer = new CreateConsumer();
        IntegrationConsumer<Integration> deleteConsumer = new DeleteConsumer();
    
        consumers = new HashMap<String,IntegrationConsumer<Integration>>();
        consumers.put("C", createConsumer);
        consumers.put("U", createConsumer);
        consumers.put("D", deleteConsumer);
        
    }

    private void log(Integration integration) {
        IFUser ifUser = new IFUser();
        ifUser.setActionFlag(integration.getActionFlag());
        ifUser.setCol1(integration.getCol1()); // username(id)
        ifUser.setCol2(integration.getCol2()); // name
        ifUser.setCol3(integration.getCol3()); // email
        ifUser.setCol4(integration.getCol4()); // mobile
        ifUser.setCol5(integration.getCol5()); // group
        ifUser.setCol6(integration.getCol6()); // 부서명
        ifUser.setCol7(integration.getCol7()); // 직급코드
        ifUser.setCol8(integration.getCol8()); // 직급명
        ifUser.setCol9(integration.getCol9());
        ifUser.setCol10(integration.getCol10());
        ifUser.setCreateId("intadmin");
        ifUser.setSourceSystem("IM");
        ifUser.setTargetSystem("ICAM");
        ifUser.setResult(integration.getResult());
        ifUser.setRemarks(integration.getRemarks());
        ifUserRepository.save(ifUser);
    };

    class CreateConsumer implements IntegrationConsumer<Integration>{
        @Override
        public void accept(Integration integration) throws Exception {
            logger.debug("User '{}' integrate action '{}'start", integration.getCol1(),integration.getActionFlag());
            UserGroup userGroup = userGroupRepository.findByUsergroupIdAndOrgId(integration.getCol5(),"KAI");
            if(userGroup==null){
                userGroup = new UserGroup();
                userGroup.setOrgId("KAI");
                userGroup.setStatus("ACTIVE");
                userGroup.setUsergroupId(integration.getCol5());
                userGroup.setGroupNameKo(integration.getCol6());
                userGroup.setDescription(integration.getCol6());
                userGroup.setGroupNameEn(integration.getCol6());
                userGroup.setCreateId("intadmin");
                userGroup.setUpdateId("intadmin");
                userGroupRepository.save(userGroup);

            }
            
            User user = userRepository.findByUsername(integration.getCol1());
            if(user==null){ 
                user = new User();
                user.setOrgId("KAI");
                user.setStatus("NOTREADY");
                user.setLang("ko-kr");
                user.setPassword(passwordEncoderBean.encode("kaiuser01!"));
                user.setCreateId("intadmin");
                user.setChangepassword(true);
            }
    
            user.setUsername(integration.getCol1());
            if(integration.getCol2()!=null){
                user.setFirstname(textEncryptor.encrypt(integration.getCol2()));
            }
            if(integration.getCol3()!=null){
                user.setEmail(textEncryptor.encrypt(integration.getCol3()));
            }
            if(integration.getCol4()!=null){
                user.setMobile(textEncryptor.encrypt(integration.getCol4()));
            }    
            
            if (user.getDefaultGroup() != null && !user.getDefaultGroup().equals(integration.getCol5()) ) {
                GroupUser groupUser = groupUserRepository.findByUsergroupIdAndUsername(user.getDefaultGroup(), integration.getCol1());
                if(groupUser != null){
                    groupUserRepository.delete(groupUser);
                }
            }
            user.setDefaultGroup(integration.getCol5());
            GroupUser groupUser = groupUserRepository.findByUsergroupIdAndUsername(integration.getCol5(), integration.getCol1());
            if(groupUser==null){
                groupUser = new GroupUser();
                groupUser.setOrgId("KAI");
                groupUser.setUsergroupId(integration.getCol5());
                groupUser.setUsername(integration.getCol1());
                groupUser.setCreateId("intadmin");
                groupUser.setUpdateId("intadmin");
                groupUserRepository.save(groupUser);
            }
            user.setUpdateId("intadmin");
            userRepository.save(user);
        }
    }

    class DeleteConsumer implements IntegrationConsumer<Integration>{
        @Override
        public void accept(Integration integration) throws Exception {
            logger.debug("User '{}' integrate action '{}'start", integration.getCol1(),integration.getActionFlag());
            User user = userRepository.findByUsername(integration.getCol1());
            if(user!=null){
                user.setStatus("INACTIVE");
                userRepository.save(user);
            }

            List<GroupUser> groupUsers = groupUserRepository.findByUsername(integration.getCol1());
            if(!groupUsers.isEmpty()){
                groupUserRepository.deleteAll(groupUsers);
            }
        }
    }

}