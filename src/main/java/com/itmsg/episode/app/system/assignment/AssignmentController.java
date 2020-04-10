package com.itmsg.episode.app.system.assignment;

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
@RequestMapping(value= AssignmentController.URL)
public class AssignmentController {

    public static final String NAME = "ASSIGNMENT";

    public static final String URL = "/api/system/assignment";

    @ESApplicationOption(application = AssignmentController.NAME, description = "Assignment application")
    public static final String READ = "ASSIGNMENT_READ";
    
    @Resource
    private AssignmentService assignmentService;

    @Resource
    private AssignmentRepository assignmentRepository;

    //@PreAuthorize("hasAuthority('"+ActionController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){        
        Object objectId = param.get("assignmentId");
        if (objectId != null) {
            return ResponseEntity.ok().body(assignmentRepository.findByAssignmentId((String)objectId));
        }
        return ResponseEntity.ok().body(assignmentRepository.findAll());
    }

    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request){  
        return ResponseEntity.ok().body(assignmentRepository.findById(id));
    }

    //@PreAuthorize("hasAuthority('"+ActionController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping  
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Assignment data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(assignmentRepository.save(data));
    }
    
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")  
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id,@RequestBody Assignment data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        Optional<Assignment> optional=assignmentRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(assignmentRepository.save(data));
    }

    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<Assignment> optional=assignmentRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        assignmentRepository.delete(optional.get());
        return ResponseEntity.ok().build();
    }
}