package com.itmsg.episode.integration.usergroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;

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
@WebService(targetNamespace = UserGroupIntegrationEndpoint.NAMESPACE_URI, portName = UserGroupIntegrationEndpoint.PORT_NAME, serviceName = UserGroupIntegrationEndpoint.SERVICE_NAME, endpointInterface = "com.itmsg.episode.integration.usergroup.UserGroupIntegration")
public class UserGroupIntegrationImpl implements UserGroupIntegration {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private IFUserGroupRepository ifUserGroupRepository;

    @Resource
    private TextEncryptor textEncryptor;

    Map<String,IntegrationConsumer<Integration>> consumers;

    @Override
    public void syncUserGroup(List<Integration> datas) {
        logger.debug("UserGroupIntegration syncGroup start");
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

    private void log(Integration integration){    
        IFUserGroup ifUserGroup = new IFUserGroup();
        ifUserGroup.setActionFlag(integration.getActionFlag());
        ifUserGroup.setCol1(integration.getCol1());   // 부서코드
        ifUserGroup.setCol2(integration.getCol2());   // 부서명
        ifUserGroup.setCol3(integration.getCol3());   // 부서장사번
        ifUserGroup.setCol4(integration.getCol4());   // 부서장명
        ifUserGroup.setCol5(integration.getCol5());   // 부서위치번호
        ifUserGroup.setCol6(integration.getCol6());   // 부서영문명
        ifUserGroup.setCol7(integration.getCol7());   // 코스트센터(회계부서)
        ifUserGroup.setCol8(integration.getCol8());   // 코스트 센터명
        ifUserGroup.setCol9(integration.getCol9());   // 상위코스트센터
        ifUserGroup.setCol10(integration.getCol10()); // 상위코스트센터명
        ifUserGroup.setCol11(integration.getCol11());  // 유효기간 시작일
        ifUserGroup.setCol12(integration.getCol12()); // 유효기간 종료일
        ifUserGroup.setCreateId("intadmin");
        ifUserGroup.setSourceSystem("IM");
        ifUserGroup.setTargetSystem("ICAM");
        ifUserGroup.setResult(integration.getResult());
        ifUserGroup.setRemarks(integration.getRemarks());
        ifUserGroupRepository.save(ifUserGroup);
    };

    class CreateConsumer implements IntegrationConsumer<Integration>{
        @Override
        public void accept(Integration integration) throws Exception {
            logger.debug("UserGroup '{}' integrate action '{}'start", integration.getCol1(),integration.getActionFlag());
            UserGroup userGroup = userGroupRepository.findByUsergroupIdAndOrgId((integration.getCol1()).substring(0, 6),"KAI");
            if(userGroup==null){
                userGroup = new UserGroup();
                userGroup.setOrgId("KAI");
                userGroup.setStatus("ACTIVE");
                userGroup.setUsergroupId((integration.getCol1()).substring(0, 6));
                userGroup.setCreateId("intadmin");
            }
            userGroup.setGroupNameKo(integration.getCol2());
            userGroup.setDescription(integration.getCol2());
            userGroup.setGroupNameEn(integration.getCol6());
            userGroup.setUpdateId("intadmin");
            userGroupRepository.save(userGroup);
        }
    }

    class DeleteConsumer implements IntegrationConsumer<Integration>{
        @Override
        public void accept(Integration integration) throws Exception {
            logger.debug("UserGroup '{}' integrate action '{}'start", integration.getCol1(),integration.getActionFlag());
            UserGroup userGroup = userGroupRepository.findByUsergroupIdAndOrgId((integration.getCol1()).substring(0, 6),"KAI");
            if(userGroup==null){
                throw new Exception(" Can not remove the user because the usergroup does not exist");
            }
            userGroup.setStatus("INACTIVE");
            userGroupRepository.save(userGroup);    
        }
    }
}