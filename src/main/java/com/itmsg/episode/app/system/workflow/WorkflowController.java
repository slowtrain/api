package com.itmsg.episode.app.system.workflow;

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

import com.itmsg.episode.app.system.action.ActionRepository;
import com.itmsg.episode.app.system.assignment.AssignmentRepository;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value= WorkflowController.URL)
public class WorkflowController {
    
    public static final String NAME="WORKFLOW";

    public static final String URL="/api/system/workflow";

    @ESApplicationOption(application = WorkflowController.NAME, description = "Workflow read option")
    public static final String READ = "WORKFLOW_READ";

    @Resource
    private WorkflowMapper workflowMapper;

    @Resource
    private WFProcessRepository wfProcessRepository;

    @Resource
    private WFNodeRepository wfNodeRepository;

    @Resource
    private WFConnectorRepository wfConnectorRepository;

    @Resource
    private WorkflowService workflowService;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private AssignmentRepository assignmentRepository;

    @Resource
    private TextEncryptor textEncryptor;


    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));     
        // List<WorkFlow> workFlowList = workFlowMapper.list(param);
   
        return ResponseEntity.ok().body(wfProcessRepository.findAll());
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/{id}")  
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id,@RequestParam Map<String,Object> param, HttpServletRequest request){
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));     
        // List<WorkFlow> workFlowList = workFlowMapper.list(param);
        return workflowService.get(id, request);
    }

   
    //@PreAuthorize("hasAuthority('"+WorkFlowController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Map<String,Object> wfProcess, HttpServletRequest request){
        return workflowService.save(wfProcess, request);
    }

    // @PreAuthorize("hasAuthority('"+OrganizationController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping(value="/{id}")       
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody WFProcess data, HttpServletRequest request) throws Exception{
        Optional<WFProcess> optional = wfProcessRepository.findById(id);
        if(!optional.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        WFProcess workflow = optional.get();
        
        // UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);

        return ResponseEntity.ok().body(wfProcessRepository.save(workflow));
    }

    //@PreAuthorize("hasAuthority('"+WorkFlowController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        Optional<WFProcess> optional=wfProcessRepository.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        wfProcessRepository.delete(optional.get());
        return ResponseEntity.ok().build();
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/{wfprocessUId}/node")  
    @ResponseBody
    public ResponseEntity<?> getNode(@PathVariable("wfprocessUId") Long wfprocessUId,@RequestParam Map<String,Object> param, HttpServletRequest request){
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));     
        // List<WorkFlow> workFlowList = workFlowMapper.list(param);
        return workflowService.getNode(wfprocessUId, param, request);
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @PutMapping(value="/{wfprocessUId}/node/{wfnodeUId}")
    @ResponseBody
    public ResponseEntity<?> putNode(@PathVariable("wfprocessUId") Long wfprocessUId,@PathVariable("wfnodeUId") Long wfnodeUId,@RequestBody WFNode data, HttpServletRequest request){
        return workflowService.putNode(wfprocessUId, wfnodeUId, data, request);
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/{wfprocessUId}/connector")  
    @ResponseBody
    public ResponseEntity<?> getConnector(@PathVariable("wfprocessUId") Long wfprocessUId,@RequestParam Map<String,Object> param, HttpServletRequest request){
        //param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));     
        // List<WorkFlow> workFlowList = workFlowMapper.list(param);
        return workflowService.getConnector(wfprocessUId, param, request);
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @PutMapping(value="/{wfprocessUId}/connector/{wfconnectorUId}")
    @ResponseBody
    public ResponseEntity<?> putConnector(@PathVariable("wfprocessUId") Long wfprocessUId,@PathVariable("wfconnectorUId") Long wfconnectorUId,@RequestBody WFConnector data, HttpServletRequest request){
        return workflowService.putConnector(wfprocessUId, wfconnectorUId, data, request);
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/action")
    @ResponseBody
    public ResponseEntity<?> getWFAction(@RequestParam Map<String,String> param, HttpServletRequest request){
        return ResponseEntity.ok().body(actionRepository.findByObjectId(param.get("objectId")));
    }

    //@PreAuthorize("hasAuthority('"+OrganizationController.READ+"')")
    @GetMapping(value="/assignment")
    @ResponseBody
    public ResponseEntity<?> getWFAssignment(@RequestParam Map<String,String> param, HttpServletRequest request){
        return ResponseEntity.ok().body(assignmentRepository.findAll());
    }

}