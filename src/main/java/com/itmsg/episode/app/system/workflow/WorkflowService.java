package com.itmsg.episode.app.system.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.app.system.action.ActionService;
import com.itmsg.episode.app.system.assignment.Assignment;
import com.itmsg.episode.app.system.assignment.AssignmentRepository;
import com.itmsg.episode.app.system.assignment.AssignmentService;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.SystemCommonService;

@Service
public class WorkflowService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private WFProcessRepository wfProcessRepository;

    @Resource
    private WFNodeRepository wfNodeRepository;

    @Resource
    private WFConnectorRepository wfConnectorRepository;

    @Resource
    private WFInstanceRepository wfInstanceRepository;

    @Resource
    private WFAssignmentRepository wfAssignmentRepository;

    @Resource
    private WFCallstackRepository wfCallstackRepository;

    @Resource
    private WorkflowMapper workflowMapper;

    @Resource
    private ActionService actionService;

    @Resource
    private AssignmentService assignmentService;

    @Resource
    JpaTransactionManager transactionManager;

    private WFProcess wfProcess;

    private WFInstance wfInstance;

    private Boolean isOption = false;

    UserDetails userDetails;

    public ResponseEntity<?> get(Long id, HttpServletRequest request) {
        Optional<WFProcess> optional = wfProcessRepository.findById(id);
        WFProcess wfProcess = null;
        if (optional.isPresent()) {
            wfProcess = optional.get();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("wfProcess", wfProcess);
            data.put("nodes", wfNodeRepository.findByWfprocessUid(wfProcess.getId()));
            data.put("connectors", wfConnectorRepository.findByWfprocessUid(wfProcess.getId()));
            return ResponseEntity.ok().body(data);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public ResponseEntity<?> getNode(Long id, Map<String, Object> param, HttpServletRequest request) {
        Optional<WFProcess> optional = wfProcessRepository.findById(id);
        WFProcess wfProcess = null;
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        wfProcess = optional.get();
        WFNode wfNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(),(String) param.get("elementId"));
        return ResponseEntity.ok().body(wfNode);
    }

    public ResponseEntity<?> getConnector(Long id, Map<String, Object> param, HttpServletRequest request) {
        Optional<WFProcess> optional = wfProcessRepository.findById(id);
        WFProcess wfProcess = null;
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        wfProcess = optional.get();
        WFConnector wfConnector = wfConnectorRepository.findByWfprocessUidAndWfconnectorId(wfProcess.getId(),(String) param.get("elementId"));
        return ResponseEntity.ok().body(wfConnector);
    }

    public ResponseEntity<?> putNode(Long wfprocessUId, Long wfnodeUId, WFNode node, HttpServletRequest request) {
        Optional<WFNode> optional = wfNodeRepository.findById(wfnodeUId);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // wfNode = optional.get();
        return ResponseEntity.ok().body(wfNodeRepository.save(node));
    }

    public ResponseEntity<?> putConnector(Long wfprocessUId, Long wfconnectorUId, WFConnector connector,HttpServletRequest request) {
        Optional<WFConnector> optional = wfConnectorRepository.findById(wfconnectorUId);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // wfNode = optional.get();
        return ResponseEntity.ok().body(wfConnectorRepository.save(connector));
    }

    public ResponseEntity<?> save(Map<String, Object> data, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("wfProcess", saveWFProcess(data, userDetails));
        result.put("nodes", saveWFNodes(data, userDetails));
        result.put("connectors", saveWFConnectors(data, userDetails));

        return ResponseEntity.ok().body(result);
    }

    public  Map<String,Object> initialize(String objectId,Long uid,String username){
        WFInstance wfInstance = workflowMapper.getWFInstance(objectId, uid);
        WFProcess wfProcess = null;
        WFNode wfNode = null;
        WFCallstack wfCallstack = null;
        List<WFConnector> wfConnectors = null;
        if(wfInstance !=null){
            wfProcess =  wfProcessRepository.findById(wfInstance.getWfprocessUid()).get();
            wfCallstack = wfCallstackRepository.findFirstByWfinstanceUidOrderByIdDesc(wfInstance.getId());
            WFConnector connector = wfConnectorRepository.findById(wfCallstack.getWfconnectorUid()).get();
            wfNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(), connector.getTargetID());
        }else {
            wfProcess =  wfProcessRepository.findByObjectIdAndRevision(objectId, workflowMapper.getMaxRevision(objectId));
            wfNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(), "Start");
        }             
        wfConnectors = wfConnectorRepository.findByWfprocessUidAndSourceID(wfProcess.getId(), wfNode.getWfnodeId());
        
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("currentWFInstance", wfInstance);
        result.put("currentWFProcess", wfProcess);
        result.put("currentWFNode", wfNode);
        result.put("currentWFConnectors", wfConnectors);
        return result;
    }
    public List<Map<String,Object>> getWFAssignment(String username,Boolean available){
        return workflowMapper.getWFAssignment(username, available);
    }

    public Map<String,Object> currentWFNode(WFInstance wfInstance, String objectId, Long uid){
        return workflowMapper.currentWFNode(objectId, uid);
    }

    public List<Map<String,Object>> currentWFConnector(WFInstance wfInstance, String objectId, Long uid){
        return workflowMapper.currentWFConnector(objectId, uid);
    }

    public List<Map<String,Object>> getWFAssignment(Boolean available){
        return getWFAssignment(null, available);
    }

    public List<Map<String,Object>> getWFAssignment(){
        return getWFAssignment(null, null);
    }

    public WFInstance getWFInstance(String objectId,Long uid){
        return workflowMapper.getWFInstance(objectId, uid);
    }

    public List<WFProcess> getWFProcess(String objectId,String status){
        return wfProcessRepository.findByObjectIdAndStatus(objectId, status);     
    }

    public WFProcess getWFProcess(String objectId,Long uid){
        WFInstance wfInstance = workflowMapper.getWFInstance(objectId, uid);
        if(wfInstance !=null){
            return wfProcessRepository.findById(wfInstance.getWfprocessUid()).get();
        }else {
            return wfProcessRepository.findByObjectIdAndRevision(objectId, workflowMapper.getMaxRevision(objectId));
        }        
    }

    public Boolean isAssigned(Long instanceUid,String username){
        return workflowMapper.isAssigned(instanceUid, username);
    }

    public Boolean progress(Map<String, Object> data, HttpServletRequest request) {
        userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Long uid = Long.valueOf((Integer) data.get("uid"));
        Long connectorUid = data.get("connectorUid") != null ? Long.valueOf((Integer) data.get("connectorUid")) : null;
        Long wfprocessUid = data.get("wfprocessUid") != null ? Long.valueOf((Integer) data.get("wfprocessUid")) : null;
        String objectId = (String) data.get("objectId");
      
        wfInstance = workflowMapper.getWFInstance(objectId, uid);
                
        if (wfInstance == null) {
            wfProcess = wfProcessRepository.findById(wfprocessUid).get();
            wfInstance = new WFInstance();
            wfInstance.setWfprocessUid(wfProcess.getId());
            wfInstance.setObjectUid(Long.valueOf(uid));
            wfInstance.setStatus("ACTIVE");
            wfInstance.setCreateId(userDetails.getUsername());
            wfInstance.setUpdateId(userDetails.getUsername());
            wfInstance = wfInstanceRepository.save(wfInstance);
        } else {
            wfProcess = wfProcessRepository.findById(wfInstance.getWfprocessUid()).get();
        }

        WFNode startNode = null;
        WFCallstack lastCallstack = wfCallstackRepository.findFirstByWfinstanceUidOrderByIdDesc(wfInstance.getId());
        if (lastCallstack == null) {
            startNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(), "Start");
        } else {
            WFConnector connector = wfConnectorRepository.findById(lastCallstack.getWfconnectorUid()).get();
            startNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(), connector.getTargetID());
        }

        if ("Data".equals(startNode.getShape_shape()) && connectorUid == null) {
            return true;
        }

        step(uid, startNode, connectorUid);

        return isOption;
    }


    private void step(Long uId, WFNode startNode,Long connectorUid){
        if("End".equals(startNode.getWfnodeId())){
            return;
        }
        
        WFConnector wfConnector = getNextConnector(uId, startNode, connectorUid);
        if (wfConnector.getActionId()!=null) {
            actionService.execute(wfConnector.getActionId(), uId);
        }

        stack(wfConnector);

        WFNode nextNode = wfNodeRepository.findByWfprocessUidAndWfnodeId(wfProcess.getId(), wfConnector.getTargetID());
        String nextShape = nextNode.getShape_shape();
        logger.info("@@@@@  {}:{} step has been started", nextNode.getShape_shape() ,nextNode.getWfnodeId());

        if("Decision".equals(nextShape)){ // condition
            step(uId,nextNode,null);
        }else if ("Process".equals(nextShape)) { // assignment
            List<String> assigners = assignmentService.getAssignments(userDetails.getUsername(),nextNode.getAssignmentId(), wfProcess.getObjectId(), uId);
            wfAssign(assigners, wfInstance.getId());
            isOption = false;
        }else if ("Data".equals(nextShape)) { // option
            List<String> assigners = new ArrayList<String>();
            assigners.add(userDetails.getUsername());
            wfAssign(assigners, wfInstance.getId());
            isOption = true;
        } else if ("Terminator".equals(nextShape)) { // start and stop
            wfInstance.setStatus("COMPLETED");
            wfInstance.setUpdateId(userDetails.getUsername());
            wfInstance = wfInstanceRepository.save(wfInstance);
            closeAssign(wfInstance.getId());
            isOption = false;
        }
        
    }

    private void stack(WFConnector wfConnector){
        WFCallstack wfCallstack = new WFCallstack();
        wfCallstack.setWfinstanceUid(wfInstance.getId());
        wfCallstack.setCreateId(userDetails.getUsername());
        wfCallstack.setWfconnectorUid(wfConnector.getId());
        wfCallstack = wfCallstackRepository.save(wfCallstack);
    }

    private WFConnector getNextConnector(Long uId, WFNode startNode,Long connectorUid){
        String startShape = startNode.getShape_shape();
        WFConnector wfConnector = null;
        List<WFConnector> wfConnectors = wfConnectorRepository.findByWfprocessUidAndSourceID(wfProcess.getId(), startNode.getWfnodeId());
        if ("Decision".equals(startShape)) {
            if (workflowMapper.isTrue(wfProcess.getObjectId(), uId, startNode.getExpression())) {
                wfConnector = wfConnectors.stream().filter((item) -> {
                    return "black".equals(item.getStyle_strokeColor());
                }).findAny().get();
            } else {
                wfConnector = wfConnectors.stream().filter((item) ->{
                    return "red".equals(item.getStyle_strokeColor());
                }).findAny().get();
            }
        } else if ("Process".equals(startShape)) {
            wfConnector = wfConnectors.get(0);
        } else if ("Data".equals(startShape)) {
            wfConnector = connectorUid!=null ? wfConnectorRepository.findById(connectorUid).get() : wfConnectors.get(0);
        } else if ("Terminator".equals(startShape)) {
            wfConnector = wfConnectors.get(0);
        }
        return wfConnector;
    }


    private WFProcess saveWFProcess(Map<String, Object> data, UserDetails userDetails) {
        Map<String, Object> wfProcessMap = (Map) data.get("wfProcess");
        wfProcess = wfProcessRepository.findByWfprocessIdAndRevision((String) wfProcessMap.get("wfprocessId"), 0);
        if (wfProcess == null) {
            wfProcess = new WFProcess();
            wfProcess.setWfprocessId((String) wfProcessMap.get("wfprocessId"));
            wfProcess.setRevision((Integer) wfProcessMap.get("revision"));
            wfProcess.setCreateId(userDetails.getUsername());
        }

        wfProcess.setObjectId((String) wfProcessMap.get("objectId"));
        wfProcess.setDescription((String) wfProcessMap.get("description"));
        wfProcess.setStatus((String) wfProcessMap.get("status"));
        wfProcess.setEnabled((Boolean) wfProcessMap.get("enabled"));
        wfProcess.setUpdateId(userDetails.getUsername());
        wfProcess = wfProcessRepository.save(wfProcess);
        return wfProcess;
    }

    private List<WFNode> saveWFNodes(Map<String, Object> data, UserDetails userDetails) {
        List<WFNode> resultNodes = new ArrayList<WFNode>();
        List<WFNode> wfNodes = wfNodeRepository.findByWfprocessUid(wfProcess.getId());
        List<Map<String, Object>> nodes = (List) data.get("nodes");
        WFNode wfNode = null;
        for (Map<String, Object> node : nodes) {
            Optional<WFNode> optional = wfNodes.stream().filter(item -> {
                return item.getWfnodeId().equals((String) node.get("wfnodeId"));
            }).findAny();

            if (optional.isPresent()) {
                wfNode = optional.get();
            } else {
                wfNode = new WFNode();
                wfNode.setWfprocessUid(wfProcess.getId());
                wfNode.setWfnodeId((String) node.get("wfnodeId"));
            }

            wfNode.setOffsetX((Integer) node.get("offsetX"));
            wfNode.setOffsetY((Integer) node.get("offsetY"));
            wfNode.setConstraints((Integer) node.get("constraints"));
            wfNode.setAnnotations_content((String) node.get("annotations_content"));
            wfNode.setStyle_fill((String) node.get("style_fill"));
            wfNode.setShape_type((String) node.get("shape_type"));
            wfNode.setShape_shape((String) node.get("shape_shape"));
            wfNode.setCreateId(userDetails.getUsername());
            wfNode.setUpdateId(userDetails.getUsername());
            wfNode = wfNodeRepository.save(wfNode);
            resultNodes.add(wfNode);
        }

        for (WFNode dbNode : wfNodes) {
            if (!nodes.stream().anyMatch(item -> {
                return dbNode.getWfnodeId().equals((String) item.get("wfnodeId"));
            })) {
                wfNodeRepository.delete(dbNode);
            }
        }
        return resultNodes;
    }

    private List<WFConnector> saveWFConnectors(Map<String, Object> data, UserDetails userDetails) {
        List<WFConnector> resultConnectors = new ArrayList<WFConnector>();
        List<WFConnector> wfConnectors = wfConnectorRepository.findByWfprocessUid(wfProcess.getId());
        WFConnector wfConnector = null;
        List<Map<String, Object>> connectors = (List) data.get("connectors");
        for (Map<String, Object> connector : connectors) {
            Optional<WFConnector> optional = wfConnectors.stream().filter(item -> {
                return item.getWfconnectorId().equals((String) connector.get("wfconnectorId"));
            }).findAny();
            if (optional.isPresent()) {
                wfConnector = optional.get();
            } else {
                wfConnector = new WFConnector();
                wfConnector.setWfprocessUid(wfProcess.getId());
                wfConnector.setWfconnectorId((String) connector.get("wfconnectorId"));
                wfConnector.setCreateId(userDetails.getUsername());
            }
            wfConnector.setSourceID((String) connector.get("sourceID"));
            wfConnector.setTargetID((String) connector.get("targetID"));
            wfConnector.setType((String) connector.get("type"));
            wfConnector.setStyle_strokeColor((String) connector.get("style_strokeColor"));
            wfConnector.setSourcePoint_x(Double.parseDouble("" + connector.get("sourcePoint_x")));
            wfConnector.setSourcePoint_y(Double.parseDouble("" + connector.get("sourcePoint_y")));
            wfConnector.setTargetPoint_x(Double.parseDouble("" + connector.get("targetPoint_x")));
            wfConnector.setTargetPoint_y(Double.parseDouble("" + connector.get("targetPoint_y")));
            wfConnector.setUpdateId(userDetails.getUsername());
            wfConnector = wfConnectorRepository.save(wfConnector);
            resultConnectors.add(wfConnector);
        }

        for (WFConnector dbConnector : wfConnectors) {
            if (!connectors.stream().anyMatch(item -> {
                return dbConnector.getWfconnectorId().equals((String) item.get("wfconnectorId"));
            })) {
                wfConnectorRepository.delete(dbConnector);
            }
        }
        return resultConnectors;
    }


    private void closeAssign(Long wfinstanceUid) {
        List<WFAssignment> wfAssignments = wfAssignmentRepository.findByWfinstanceUidAndAvailable(wfinstanceUid, true);
        wfAssignments = wfAssignments.stream().map(item -> {
            item.setAvailable(false);
            item.setUpdateId(userDetails.getUsername());
            return item;
        }).collect(Collectors.toList());
        wfAssignmentRepository.saveAll(wfAssignments);
    }

    private void wfAssign(List<String> assigners, Long wfinstanceUid) {
        closeAssign(wfinstanceUid);
        WFAssignment wfAssignment = null;
        for (String assigner : assigners) {
            wfAssignment = new WFAssignment();
            wfAssignment.setWfinstanceUid(wfInstance.getId());
            wfAssignment.setAvailable(true);
            wfAssignment.setCreateId(userDetails.getUsername());
            wfAssignment.setUpdateId(userDetails.getUsername());
            wfAssignment.setUsername(assigner);
            wfAssignmentRepository.save(wfAssignment);
        }
    }

  

    
  
}
