package com.itmsg.episode.app.system.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value= ActionController.URL)
public class ActionController {
    
    public static final String NAME="ACTION";

    public static final String URL="/api/system/action";

    @ESApplicationOption(application = ActionController.NAME, description = "Action application")
    public static final String READ = "ACTION_READ";
    
    @Resource
    private ActionService actionService;

    @Resource
    private ActionRepository actionRepository;

    //@PreAuthorize("hasAuthority('"+ActionController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){        
        Object actionId = param.get("actionId");
        if (actionId != null) {
            return ResponseEntity.ok().body(actionRepository.findByActionId((String)actionId));
        }
        return ResponseEntity.ok().body(actionRepository.findAll());
    }

    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){  
        return ResponseEntity.ok().body(actionRepository.findById(id));
    }

    //@PreAuthorize("hasAuthority('"+ActionController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping  
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Action data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(actionRepository.save(data));
    }
    
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")  
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id,@RequestBody Action data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        Optional<Action> optional=actionRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(actionRepository.save(data));
    }

    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<Action> optional=actionRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        actionRepository.delete(optional.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/{actionUid}/test/{objectUid}") 
    @ResponseBody
    public ResponseEntity<?> test(@PathVariable("actionUid") Long actionUid, @PathVariable("objectUid") Long objectUid, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok().body(actionService.test(actionUid, objectUid,request,response));
    }

}