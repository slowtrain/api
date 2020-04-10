package com.itmsg.episode.app.system.action;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.system.ESAction;

import org.springframework.stereotype.Component;

@ESAction
@Component("com.itmsg.episode.app.system.action.TestAction")
public class TestAction implements CustomAction{

    @Override
    public void execute(Object object) {
        CI ci = (CI)object;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ execute");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ execute");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ execute");
        System.out.println("@@@@ ciname : "+ci.getCiname());
    }

}