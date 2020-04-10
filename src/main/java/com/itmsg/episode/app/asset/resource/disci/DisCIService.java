package com.itmsg.episode.app.asset.resource.disci;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.ci.CI;
import com.itmsg.episode.app.asset.resource.ci.CIMapper;
import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.app.asset.resource.ci.CIService;
import com.itmsg.episode.app.asset.resource.disci.util.CIPromoter;
import com.itmsg.episode.app.asset.resource.disci.util.Promoter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DisCIService {

    private static final String[] DATA_FIELDS = {
        "extaln1", "extaln2", "extaln3", "extaln4", "extaln5", "extaln6", "extaln7", "extaln8", "extaln9", "extaln10",
        "extaln11", "extaln12", "extaln13", "extaln14", "extaln15", "extaln16", "extaln17", "extaln18", "extaln19", "extaln20", 
        "extnum1", "extnum2", "extnum3", "extnum4", "extnum5", "extdate1", "extdate2", "extdate3", "extdate4", "extdate5"
        ,"extbool1", "extbool2", "extbool4", "extbool5" };
    
    private static final String[] HISTORY_EXCEPT_FIELDS = {
            "extaln1", "extaln2", "extaln3", "extaln4", "extaln6", "extaln7", "extaln8", "extaln9", "extaln10",
            "extaln11", "extaln12", "extaln13", "extaln14", "extaln15", "extaln16", "extaln17", "extaln18", "extaln19", "extaln20", 
            "extdate1", "extdate2", "extdate3", "extdate4", "extdate5"
            ,"extbool1", "extbool2", "extbool3", "extbool4", "extbool5" };    

    private static final String [] DISCI_TYPES = {"ComputerSystem", "NetDevice", "WebApplicationServer", "DatabaseServer"};

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private DisCIRepository disCIRepository;

    @Resource
    private PromoteLogRepository promoteLogRepository;

    @Resource
    private DisCIMapToCIRepository disCIMapToCIRepository;
    
    @Resource
    private CIService ciService;

    @Resource
    private CIRepository cIRepository;

    @Resource
    private CIMapper cIMapper;

    @Resource
    private DisCIMapper disCIMapper;

    Map<String,Object>  params;

    @PostConstruct
    public void init() {
        params = new HashMap<String,Object>();
        params.put("orgId","KAI");

    }

    public void promote(String dataSource) {
        Promoter promoter = (Promoter)applicationContext.getBeansWithAnnotation(CIPromoter.class).get(dataSource);
        List<DisCI> disCIs =  disCIMapper.getPromoteTarget(dataSource);
        for (DisCI disCI : disCIs) {
            try{
                promoter.promote(disCI);
            }catch(Exception e){
                log(e, disCI);
            }
        }
    }

    public void promoteRelations() {
        disCIMapper.insertCIRelationHisInactive();
        disCIMapper.updateCIRelationsInactive();
        disCIMapper.insertCIRelationHisActive();
        disCIMapper.updateCIRelationsActive();
        disCIMapper.addCIRelations();
    }

    public void insertRPASystem(){
        disCIMapper.insertRPASystem();
    }

    public void insertDisCIMapToCI(){
        disCIMapper.insertDisCIMapToCI();
    }

      /**
     * name : getEmsDisCI
     * description : IT 인프라 EMS 작업현황
     * @param : null
     * @return : ResponseEntity
     */
    public Map<String, Object> getEmsDisCI() {

        Map<String,Object> result = new HashMap<>();
        for(String discitype : DISCI_TYPES) {
            result.put(discitype, disCIMapper.emsDisCI(discitype));
        }
        return result;
    }

    @Component("BFI")
    @CIPromoter
    class BigFixPromoter implements Promoter {
    
        @Override
        public void promote(DisCI disCI) throws Exception {
            logger.debug("BigFixPromoter id {} ciId {} datasource {}",disCI.getId(),disCI.getDisciId(),disCI.getDatasrc());
            
            CI ci = getCI(disCI);
            if(ci == null){
                List<DisCI> sameHosts =disCIMapper.getSameHostAndPromoted(disCI);
                if(!sameHosts.isEmpty()){
                    ci = (disCI.getDisciSerialnumber() != null) ? getSerialMatched(disCI, sameHosts) : getIPMatched(disCI, sameHosts);
                }
            }

            if(ci == null){
                ci = createCI(disCI);
            }
            
            saveCI(ci, disCI);
            saveDisCI(ci, disCI);
            disCIMapper.updateCISW(disCI);
            if(disCI.getIsdelete()){
                disCIMapper.deleteCISW(disCI);
            }
        }
    }

    @Component("TADDM")
    @CIPromoter
    class TaddmPromoter implements Promoter {

        @Override
        public void promote(DisCI disCI) throws Exception {
            logger.debug("TaddmPromoter id {} ciId {} datasource {}",disCI.getId(),disCI.getDisciId(),disCI.getDatasrc());
            
            CI ci = getCI(disCI);
            if(ci == null){
                if("COMPSYS".equals(disCI.getDisigtype())){
                    List<DisCI> sameHosts =disCIMapper.getSameHostAndPromoted(disCI);
                    if(!sameHosts.isEmpty()){
                        ci = (disCI.getDisciSerialnumber() != null) ? getSerialMatched(disCI, sameHosts) : getIPMatched(disCI, sameHosts);
                    }
                }
            }

            if(ci == null){
                ci = createCI(disCI);
            }
            List<DisCI> sameci = disCIMapper.getDisCIByCIid(ci.getCiId());
            ci.setIsdelete(sameci.stream().allMatch(DisCI::getIsdelete));
            saveCI(ci, disCI);
            saveDisCI(ci, disCI);
        }
    }

    @Component("WATCHALL")
    @CIPromoter
    class WatchAllPromoter implements Promoter {
        
        @Override
        public void promote(DisCI disCI) throws Exception {
            logger.debug("WatchAllPromoter id {} ciId {} datasource {}",disCI.getId(),disCI.getDisciId(),disCI.getDatasrc());
            
            CI ci = getCI(disCI);
            if(ci == null){
                if("COMPSYS".equals(disCI.getDisigtype())){
                    List<DisCI> sameHosts =disCIMapper.getSameHostAndPromoted(disCI);
                    if(!sameHosts.isEmpty()){    
                        ci = getIPMatched(disCI,sameHosts);
                    }
                }
            }
            
            if(ci!=null){
                ci = saveCI(ci, disCI);
                saveDisCI(ci, disCI);
            }
        }
    } 
    
    @Component("NUTANIX")
    @CIPromoter
    class NutanixPromoter implements Promoter {
        @Override
        public void promote(DisCI disCI) throws Exception {
            logger.debug("NutanixPromoter id {} ciId {} datasource {}",disCI.getId(),disCI.getDisciId(),disCI.getDatasrc());
            
            CI ci = getCI(disCI);
            if(ci == null){
                if("COMPSYS".equals(disCI.getDisigtype())){
                    List<DisCI> sameHosts =disCIMapper.getSameHostAndPromoted(disCI);
                    if(!sameHosts.isEmpty()){
                        ci = getSerialMatched(disCI, sameHosts);
                    }
                }else{
                    ci = createCI(disCI);
                }
            }

            if(ci!=null){
                ci = saveCI(ci, disCI);
                saveDisCI(ci, disCI);
            }

        }
    }

    @Component("SAPPO")
    @CIPromoter
    class SapPoPromoter implements Promoter {
        @Override
        public void promote(DisCI disCI) throws Exception {
            logger.debug("SapPoPromoter id {} ciId {} datasource {}",disCI.getId(),disCI.getDisciId(),disCI.getDatasrc());            
            CI ci = getCI(disCI);
            if(ci == null){
                ci = createCI(disCI);
            }
            if(disCI.getIsdelete()){
                ci.setIsdelete(true);
                ci.setStatus("INACTIVE");
            }
            ci = saveCI(ci, disCI);
            saveDisCI(ci, disCI);
        }
    }


    private void log(Exception e, DisCI disCI){
        PromoteLog promoteLog = new PromoteLog();
        promoteLog.setDisciId(disCI.getDisciId());
        promoteLog.setDatasrc(disCI.getDatasrc());
        promoteLog.setDescription(e.getMessage());
        promoteLog.setCreateId("intadmin");
        promoteLogRepository.save(promoteLog);
    }

    private CI getIPMatched(DisCI disCI, List<DisCI> sameHosts) {
        CI ci =null;
        if(disCI.getDisciIpaddress()!=null){
            Optional<DisCI> ipMatched = sameHosts.stream().filter((sameHost)->{
                return Arrays.stream(disCI.getDisciIpaddress().split(",")).anyMatch((ip)->{
                    return sameHost.getDisciIpaddress()!=null && sameHost.getDisciIpaddress().contains(ip);
                });
            }).findFirst();
            
            if(ipMatched.isPresent()){
                ci = cIRepository.findByCiId(ipMatched.get().getCiId());
            }
        }
        
        return ci;
    }

    private CI getSerialMatched(DisCI disCI, List<DisCI> sameHosts) {
        CI ci =null;
        if(disCI.getDisciSerialnumber()!=null){
            Optional<DisCI> serialMatched = sameHosts.stream().filter((sameHost)->{
                return disCI.getDisciSerialnumber().equals(sameHost.getDisciSerialnumber());
            }).findFirst();
            
            if(serialMatched.isPresent()){
                ci = cIRepository.findByCiId(serialMatched.get().getCiId());
            }
        }
        
        return ci;
    }

    private CI createCI(DisCI disCI) {
        CI ci = new CI();
        ci.setOrgId("KAI");
        ci.setCiId(cIMapper.ciId(params));
        ci.setClassstructureId(disCI.getClassstructureId());
        ci.setDescription(disCI.getDiscidescription());
        ci.setIsdelete(disCI.getIsdelete());
        ci.setCreateId("intadmin");
        ci.setUpdateId("intadmin");
        ci.setUserdefine(false);
        ci.setCiname(disCI.getDisciname());
        ci.setStatus("ACTIVE");
        return ci;
    }

    private CI getCI(DisCI disCI){
        CI ci = null;
        if(disCI.getCiId()!=null){
            ci = cIRepository.findByCiId(disCI.getCiId());
        }
        return ci;
    }

    private CI saveCI(CI ci,DisCI disCI)throws Exception{
        boolean requireHistory = ci.getCiId()!= null && "COMPSYS".equals(disCI.getDisigtype()) ? true : false;
        CI oldCI = null;
        if(requireHistory){
            oldCI = (CI)ci.clone();
        }    
        // ci.setClassstructureId(disCI.getClassstructureId());  
        Class disClass = DisCI.class;
        Class ciClass = CI.class;
        Field disField = null;
        Field ciField = null;
        Object disValue = null;
        for(String field:DATA_FIELDS){
            
            disField = disClass.getDeclaredField(field);
            disField.setAccessible(true);
            disValue = disField.get(disCI);
            
            ciField = ciClass.getDeclaredField(field);
            ciField.setAccessible(true);
            if(disValue!=null){
                disValue = field.startsWith("extaln") ? cIMapper.convert("ci", field, disCI.getClassstructureId(), (String)disValue) : disValue;
                ciField.set(ci,disValue);
            }
        }
        ci = cIRepository.save(ci);
        if(requireHistory){
            ciService.makeCIHist("CI","UPDATE",oldCI, ci,HISTORY_EXCEPT_FIELDS);
        }

        return ci;
    }

    private DisCI saveDisCI(CI ci, DisCI disCI)throws Exception{
        disCI.setCiId(ci.getCiId());
        disCI.setIsupdate(false);
        disCI.setUpdateId("intadmin");
        return disCIRepository.save(disCI);
    }

  
}