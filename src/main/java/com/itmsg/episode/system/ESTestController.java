package com.itmsg.episode.system;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.itmsg.episode.app.asset.resource.ci.*;
import com.itmsg.episode.app.asset.resource.disci.DisCIService;
import com.itmsg.episode.app.system.action.Action;
import com.itmsg.episode.app.system.action.ActionRepository;
import com.itmsg.episode.app.system.action.CustomAction;
import com.itmsg.episode.app.system.application.Application;
import com.itmsg.episode.util.Notice;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.python.core.PyObject;
import org.python.util.*;

@Controller
public class ESTestController {

    @Resource
    ApplicationContext applicationContext;

    @Resource
    DisCIService disCIService;

    @Resource
    CIRepository ciRepository;

    @Resource
    ActionRepository actionRepository;

    @Resource
    Notice notice;

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    JpaTransactionManager transactionManager;


    @RequestMapping(value = "/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body("Hello Episode");
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> test(@RequestParam Map<String, String> param)throws Exception {
        return ResponseEntity.ok().body("test completed");
    }

    /*
    @GetMapping(value = "/test")
    public ResponseEntity<?> test(@RequestParam Map<String,String> param) throws UnknownHostException, ClassNotFoundException, NoSuchFieldException, SecurityException {

        String ciname = (String)param.get("ciname");
        Class clazz = Class.forName("com.itmsg.episode.app.asset.resource.ci.CI");
        EntityManager entitymanger = transactionManager.getEntityManagerFactory().createEntityManager();
        SimpleJpaRepository repository = new SimpleJpaRepository(clazz,entitymanger);
        Object _data = repository.getOne(Long.valueOf(10));
        System.out.println("@@@@@@ classname : "+repository.getOne(Long.valueOf(10)));
        
        List<Action> action = actionRepository.findByActionId("CHANGE_CISTATUS");
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set("_data",clazz.cast(_data));
        // interpreter.exec("print 'from java : ' + str(_data.getCiname())");
        interpreter.exec(action.get(0).getJython());
        _data = interpreter.get("_data", clazz);

        EntityTransaction transaction =  entitymanger.getTransaction();
        transaction.begin();
        repository.save(_data);
        transaction.commit();
        System.out.println(" status has been changed");
      
        return ResponseEntity.ok().body("");
    }
    */

    @RequestMapping(value = "/password")
    public ResponseEntity<?> password() throws UnknownHostException {
        return ResponseEntity.ok().body(passwordEncoderBean.encode("esadmin"));
    }

}