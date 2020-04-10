package com.itmsg.episode.app.system.action;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itmsg.episode.system.ESAction;
import com.itmsg.episode.system.SystemCommonService;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;

import javassist.bytecode.Descriptor.Iterator;

@Service
public class ActionService {

    @Resource
    private ActionRepository actionRepository;

    @Resource
    SystemCommonService systemCommonService;

    @Resource
    JpaTransactionManager transactionManager;

    @Resource
    ApplicationContext applicationContext;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> get(Long id,HttpServletRequest request){
        return ResponseEntity.ok().build();
        //return ResponseEntity.ok().body(actionRepository.findById(id));
    }

    public void execute(String actionId,Long objectUid){
        try {
            Action action  = actionRepository.findByActionId(actionId).get(0);
            String type = action.getType();
            Map<String,String> table = systemCommonService.tableList(action.getObjectId()).get(0);
            Class clazz = Class.forName(table.get("className"));
            EntityManager entitymanger = transactionManager.getEntityManagerFactory().createEntityManager();
            SimpleJpaRepository repository = new SimpleJpaRepository(clazz,entitymanger);
            Object _data = repository.getOne(objectUid);
            
            if ("JYTHON".equals(type)) {

    
                logger.info("Action Test '{}'", action.getActionId());
                logger.info(action.getJython());
                PythonInterpreter interpreter = new PythonInterpreter();
                interpreter.setErr(System.out);
                interpreter.setOut(System.out);
                interpreter.set("_data",clazz.cast(_data));
                interpreter.set("_data2",_data);
                interpreter.exec(action.getJython());
                
                _data = interpreter.get("_data", clazz);
                interpreter.close();
    
                EntityTransaction transaction =  entitymanger.getTransaction();
                transaction.begin();
                repository.save(_data);
                transaction.commit();
            } else if ("CLASS".equals(type)){
                Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESAction.class);
                CustomAction customAction = (CustomAction)beans.get(action.getClassName());
                customAction.execute(_data);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Object test(Long actionUid,Long objectUid,HttpServletRequest request, HttpServletResponse response){
        try {
            Optional<Action> optional = actionRepository.findById(actionUid);
            if(!optional.isPresent()){
                return HttpStatus.BAD_REQUEST.name();
            }
            
            Action action = optional.get();
            Map<String,String> table = systemCommonService.tableList(action.getObjectId()).get(0);
            Class clazz = Class.forName(table.get("className"));
            EntityManager entitymanger = transactionManager.getEntityManagerFactory().createEntityManager();
            SimpleJpaRepository repository = new SimpleJpaRepository(clazz,entitymanger);
            Object _data = repository.getOne(objectUid);

            logger.info("Action Test '{}'", action.getActionId());
            logger.info(action.getJython());
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.setErr(response.getOutputStream());
            interpreter.setOut(response.getOutputStream());
            interpreter.set("_data",clazz.cast(_data));
            interpreter.set("_data2",_data);
            interpreter.exec(action.getJython());
            
            _data = interpreter.get("_data", clazz);
            interpreter.close();

            EntityTransaction transaction =  entitymanger.getTransaction();
            transaction.begin();
            repository.save(_data);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
      
        return " Action test has been finished";
        //return ResponseEntity.ok().body(actionRepository.findById(id));
    }

}