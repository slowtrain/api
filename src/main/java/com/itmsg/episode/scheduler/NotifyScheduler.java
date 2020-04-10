package com.itmsg.episode.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.ci.CIService;
import com.itmsg.episode.app.asset.resource.disci.DisCIService;
import com.itmsg.episode.app.system.organization.OrganizationMapper;
import com.itmsg.episode.app.system.usergroup.UserGroupMapper;
import com.itmsg.episode.system.ESScheduler;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.Notice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component(NotifyScheduler.SCHEDULER_ID)
@ESScheduler(description="Notify")
public class NotifyScheduler extends TaskScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String SCHEDULER_ID = "NotifyScheduler";
    
    @PostConstruct
    @Override
    protected void initialize() {
        schedulerId = SCHEDULER_ID;
    }

    @Resource
    TextEncryptor textEncryptor;

    @Resource
    DisCIService disCIService;

    @Resource
    SystemCommonService systemCommonService;

    @Resource
    OrganizationMapper organizationMapper;

    @Resource
    UserGroupMapper userGroupMapper;

    @Resource
    Notice notice;

    @Value("${skype.integration.sendor}")
    String skypeSendor;
    
    @Value("${system.admin.email}")
	String mailSendor;

    @Override
    public void execute() {
        logger.debug("{} execute !!!!!! ", NotifyScheduler.class.getName());
        Map<String,Object[]> notifications = new HashMap<String,Object[]>();
        notifications.put("NOTI_CI_NEW", new Object[]{"IT자원 신규생성 알림","IT자원 %s 이 신규생성 되었습니다.", true, true});
        notifications.put("NOTI_DISCI_NOTSCAN", new Object[]{"IT자원 스캔 미완료 알림","IT자원 %s 의 Bigfix나 Taddm 의 스캔작업이 이 2주동안 완료되지 않았습니다. ", true, true });
        notifications.put("NOTI_CI_INACTIVE", new Object[]{"IT자원 상태변경 알림","IT자원 %s 상태가 INACTIVE로 변경되었습니다. ", true, true });
        notifications.put("NOTI_EMS_DELETE", new Object[]{"IT자원 EMS Agent 삭제 알림","IT자원 %s 의 EMS AGENT가 삭제 되었습니다.", true, true });

        String[] notiTypes = parameter.split(",");
        
        for(String notiType : notiTypes){
            Object[] notification = notifications.get(notiType);
            String[] receiverGroups =  organizationMapper.getNotiGroups(notiType).split(",");
            List<Map<String,Object>> objects = systemCommonService.getNotifyObject(notiType);
            for(Map<String,Object> object:objects){
                for(String receiverGroup : receiverGroups){
                    List<String> receivers = userGroupMapper.getGroupUserEmail(receiverGroup);
                    receivers = receivers.stream().map((receiver) -> textEncryptor.decrypt(receiver)).collect(Collectors.toList());
                    if((Boolean)notification[2]){
                        notice.notifyByMail(mailSendor, receivers, (String)notification[0],String.format((String)notification[1],(String)object.get("col1")));
                    }
                    receivers = userGroupMapper.getGroupUserSkype(receiverGroup);
                    if((Boolean)notification[3]){
                        notice.notifyBySkype(skypeSendor, receivers, (String)notification[0],String.format((String)notification[1],(String)object.get("col1")), Notice.SKYPE_SEND_TYPE_TXT);
                    }
                }
            }
        }
    }
}
