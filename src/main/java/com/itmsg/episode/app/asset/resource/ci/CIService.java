/**
* IT 자원  비지니스 서비스 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.persistence.Transient;

import com.itmsg.episode.app.asset.resource.cihis.CIhis;
import com.itmsg.episode.app.asset.resource.cihis.CIhisRepository;
import com.itmsg.episode.app.system.application.AppLabel;
import com.itmsg.episode.app.system.application.AppLabelRepository;
import com.itmsg.episode.app.system.classification.Classancestor;
import com.itmsg.episode.app.system.classification.ClassancestorRepository;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.util.StringUtils;
    
@Service
public class CIService {

    private static final String [] INFRA_ANCESTOR = {"GROUP_1", "SOFTWARE", "NETWORK", "SERVER", "BUSINESSSYSTEM"};
    private static final String [] SCANCI_TYPES = {"SERVER", "SW_DBSW", "SW_MIDDLESW"};

    public static final String HIS_TYPE_CI = "CI";
    public static final String HIS_TYPE_SPEC = "SPEC";
    public static final String HIS_TYPE_RELATION = "RELATION";

    public static final String HIS_CRDTYPE_UPDATE = "UPDATE";
    public static final String HIS_CRDTYPE_DELETE = "DELETE";

    @Resource
    private CIMapper ciMapper;

    @Resource
    private CIRepository ciRepository;

    @Resource
    private CIspecRepository cispecRepository;

    @Resource
    private CIrelationRepository cirelationRepository;

    @Resource
    private CIhisRepository cihisRepository;

    @Resource
    private CIrelationmapRepository cirelationmapRepository;

    @Resource
    private ClassancestorRepository classancestorRepository;

    @Resource
    private AppLabelRepository appLabelRepository;
    
    @Resource
    private UserRepository userRepository;
   
    /**
     * name : getCIExtNm
     * description : extaln, extnum, extbool... item name
     * @param : Map param
     * @return : ResponseEntity
     */
    public Map<String, Object> getCIExtNm (Map<String, Object> param) {        
        TreeMap<String, Object> ciExtNmData = ciMapper.ciExtNmData(param);

        TreeMap<String, Object> ciExtNmMap = new TreeMap<String, Object>();        
        if(ciExtNmData != null){
            for(String key : ciExtNmData.keySet()) {
                String ciExtNm = (String)ciExtNmData.get(key);
                ciExtNmMap.put(key, ciExtNm);
            }
        }
        return ciExtNmMap;
    }

    public void makeCIHist(String type, String crdType, Object oldDataObj, Object newDataObj) throws IllegalArgumentException, IllegalAccessException {
        makeCIHist(type, crdType, oldDataObj, newDataObj, null);
    }
    /**
     * name : makeCIHist
     * description : make spec ci relation history
     * @param : String type, String crdType, Object oldDataObj, Object newDataObj
     * @return : ResponseEntity
     */
    public void makeCIHist(String type, String crdType, Object oldDataObj, Object newDataObj, String[] exptCol) throws IllegalArgumentException, IllegalAccessException {
        Field[] oldFields = oldDataObj.getClass().getDeclaredFields();
        Field[] newFields = newDataObj.getClass().getDeclaredFields();
        String oldKey = null;
        String newKey = null;
        Object odlValObj = null;
        Object newValObj = null;
        String [] exptList = {"TABLE_NAME","id","createId","createDt","updateId","updateDt","userdefine","endDt"};
        CIhis cihis = null;
        CIhis cihisRel = null;
        Map<String, Object> extNms = new HashMap<String, Object>();
        Map<String, String> labels = new HashMap<String, String>();        

        String orgId = "";
        String ciId = "";
        String createId = "";
        String updateId = "";
        String classspecId = "";
        String relationname = "";
        String sourceci = "";
        String targetci = "";  

        if(exptCol !=  null){
            String [] exptListTemp = new String [exptList.length + exptCol.length];
            System.arraycopy(exptList, 0, exptListTemp, 0, exptList.length);
            System.arraycopy(exptCol, 0, exptListTemp, exptList.length, exptCol.length);
            exptList = exptListTemp;
        }

        if(HIS_TYPE_CI.equals(type)){
            orgId = ((CI)newDataObj).getOrgId();
            ciId = ((CI)newDataObj).getCiId();
            createId = ((CI)newDataObj).getCreateId();
            updateId = ((CI)newDataObj).getUpdateId();
            Map<String, Object> param = new HashMap<String, Object>();

            param.put("classstructureId", (String)((CI) newDataObj).getClassstructureId());
            param.put("lang", "en-us");

            extNms = getCIExtNm(param);

            User user = userRepository.findByUsername(updateId);
            List<AppLabel> appLabelList = appLabelRepository.findByAppIdAndLang("CI", "en-us");
    
            for(AppLabel appLabel : appLabelList){
                labels.put(appLabel.getName(), appLabel.getDescription());    
            }
        }else if(HIS_TYPE_SPEC.equals(type)){
            orgId = ((CIspec)newDataObj).getOrgId();
            ciId = ((CIspec)newDataObj).getCiId();
            createId = ((CIspec)newDataObj).getCreateId();
            updateId = ((CIspec)newDataObj).getUpdateId();
            classspecId = ((CIspec)newDataObj).getClassspecId();
        }else if(HIS_TYPE_RELATION.equals(type)){
            orgId = ((CIrelation)newDataObj).getOrgId();
            ciId = ((CIrelation)newDataObj).getCiId();
            createId = ((CIrelation)newDataObj).getCreateId();
            updateId = ((CIrelation)newDataObj).getUpdateId();
            relationname = ((CIrelation)newDataObj).getRelationname();
            sourceci = ((CIrelation)newDataObj).getSourceci();
            targetci = ((CIrelation)newDataObj).getTargetci();            
        }
       
        if(HIS_CRDTYPE_UPDATE.equals(crdType)){
            String chgdetail = "";
            String chgdescription = "";
            
            for(int i=0; i < oldFields.length; i++){
                
                Field oldField = oldFields[i];
                Field newField = newFields[i];

                oldField.setAccessible(true);
                newField.setAccessible(true);

                oldKey = oldField.getName();
                newKey = newField.getName();
                
                odlValObj = oldField.get(oldDataObj);
                newValObj = newField.get(newDataObj);
                
                Arrays.sort(exptList);

                if(Arrays.binarySearch(exptList, oldKey) > 0){
                    continue;
                }

                if(oldField.getAnnotation(Transient.class) instanceof Transient){
                    continue;
                }

                if(odlValObj == null){
                    odlValObj = "null";
                }

                if(newValObj == null){
                    newValObj = "null";
                }
                
                if(odlValObj instanceof Long){
                    odlValObj = String.valueOf(odlValObj);
                }

                if(newValObj instanceof Long){
                    newValObj = String.valueOf(newValObj);
                }

                if(odlValObj instanceof Date){
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
                    String date = format.format(odlValObj);
                    odlValObj = date;
                }

                if(newValObj instanceof Date){
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
                    String date = format.format(newValObj);
                    newValObj = date;
                }    

                if(odlValObj instanceof BigDecimal){
                    odlValObj = odlValObj.toString();
                    odlValObj = ((String)odlValObj).replaceAll(".0000", "");
                }
                
                if(newValObj instanceof BigDecimal){
                    newValObj = newValObj.toString();
                    newValObj = ((String)newValObj).replaceAll(".0000", "");
                }

                if(odlValObj instanceof Boolean){
                    if((Boolean)odlValObj){
                        odlValObj = "true";        
                    } else {
                        odlValObj = "false";            
                    }
                }
                
                if(newValObj instanceof Boolean){
                    if((Boolean)newValObj){
                        newValObj = "true";        
                    } else {
                        newValObj = "false";            
                    }
                }

                if(!odlValObj.equals(newValObj)){
                    String separator = "";

                    if(!StringUtils.isNull(chgdetail)){
                        separator = ", ";        
                    }
                    if(HIS_TYPE_CI.equals(type)){
                        String item = (String)extNms.get(oldKey);
                        if(StringUtils.isNull(item)){
                            item = labels.get(oldKey);
                        } 
                        if(StringUtils.isNull(item)){
                            item = oldKey;
                        }
                        
                        chgdetail = chgdetail + separator + item;
                        chgdescription = chgdescription + separator + item + " : " + odlValObj + ">>" + newValObj;
                    }else if(HIS_TYPE_SPEC.equals(type)){
                        chgdetail = chgdetail + separator + classspecId;
                        chgdescription = chgdescription + separator + classspecId + " : " + odlValObj + ">>" + newValObj;
                    }else if(HIS_TYPE_RELATION.equals(type)){
                        chgdetail = "Status";
                        chgdescription = "Status of relation \""+relationname+"\" source \""+sourceci+"\" target \""+targetci+"\" was changed to \""+ (String)newValObj +"\"";
                    }
                }  
            }
            
            cihis = new CIhis(); 
            
            if(!StringUtils.isNull(chgdescription)){
                cihis.setOrgId(orgId);
                cihis.setCiId(ciId);
                cihis.setChgtype(type);
                cihis.setCreateId(createId);
                cihis.setUpdateId(updateId);

                cihis.setChgdetail(chgdetail);
                cihis.setChgdescription(chgdescription);
                
                cihisRepository.save(cihis);

                if(HIS_TYPE_RELATION.equals(type)){
                    cihisRel = new CIhis();
                    if(ciId.equals(sourceci)){
                        cihisRel.setCiId(targetci);
                    }else{
                        cihisRel.setCiId(sourceci);
                    }       
                    cihisRel.setOrgId(orgId);
                    cihisRel.setChgtype(type);
                    cihisRel.setCreateId(createId);
                    cihisRel.setUpdateId(updateId);
                    cihisRel.setChgdetail(chgdetail);
                    cihisRel.setChgdescription(chgdescription);

                    cihisRepository.save(cihisRel);
                }
            }            
        }else if(HIS_CRDTYPE_DELETE.equals(crdType)){
            String chgdetail = "";
            String chgdescription = "";

            cihis = new CIhis(); 
            cihis.setOrgId(orgId);
            cihis.setCiId(ciId);
            cihis.setChgtype(type);
                    
            if(HIS_TYPE_CI.equals(type)){
                cihis.setChgitem("ALL");
                cihis.setFromvalue("DELETE");
                cihis.setTovalue("DELETE");
                chgdetail = ciId;
                chgdescription = "DELETE";
            }else if(HIS_TYPE_SPEC.equals(type)){
                cihis.setChgitem(classspecId);
                cihis.setFromvalue("DELETE");
                cihis.setTovalue("DELETE");
                chgdetail = classspecId;
                chgdescription = "DELETE";
            }else if(HIS_TYPE_RELATION.equals(type)){
                cihis.setChgitem(oldKey);
                cihis.setFromvalue("DELETE");
                cihis.setTovalue("DELETE");
                chgdetail = "DELETE";
                chgdescription = "relation \""+relationname+"\" source \""+sourceci+"\" target \""+targetci+"\" was Deleted";
                // cihis.setChgitem(oldKey);
                // cihis.setFromvalue("ACTIVE");
                // cihis.setTovalue("INACTIVE");
                // chgdetail = "STATUS";
                // chgdescription = "Status of relation \""+relationname+"\" source \""+sourceci+"\" target \""+targetci+"\" was changed to \"INACTIVE\"";
            }
            
            cihis.setChgdetail(chgdetail);
            cihis.setChgdescription(chgdescription);
            cihis.setCreateId(createId);
            cihis.setUpdateId(updateId);
            
            cihisRepository.save(cihis);
            if(HIS_TYPE_RELATION.equals(type)){
                cihisRel = new CIhis();
                if(ciId.equals(sourceci)){
                    cihisRel.setCiId(targetci);
                }else{
                    cihisRel.setCiId(sourceci);
                }       
                cihisRel.setOrgId(orgId);
                cihisRel.setChgtype(type);
                cihisRel.setChgitem(oldKey);
                cihisRel.setFromvalue("DELETE");
                cihisRel.setTovalue("DELETE");
                cihisRel.setChgdescription("relation \""+relationname+"\" source \""+sourceci+"\" target \""+targetci+"\" was Deleted");  
                // cihisRel.setFromvalue("ACTIVE");
                // cihisRel.setTovalue("INACTIVE");
                // cihisRel.setChgdescription("Status of relation \""+relationname+"\" source \""+sourceci+"\" target \""+targetci+"\" was changed to \"INACTIVE\"");  
                cihisRel.setCreateId(createId);
                cihisRel.setUpdateId(updateId);
                cihisRepository.save(cihisRel);
            }
        }
    }

    /**
     * name : makeRelationmap
     * description : 분류의 하위 데이터까지 관계를 자동으로 만들어 주는 기능
     * @param : Map data
     * @return : ResponseEntity
     */
    public void makeRelationmap(Map<String, Object> data){
        String orgId = (String)data.get("orgId");
        Boolean childrenYn = (Boolean)data.get("childrenYn");
        String sourceclass = (String)data.get("sourceclass");
        String targetclass = (String)data.get("targetclass");
        String relationname = (String)data.get("relationname");
        String createId = (String)data.get("createId");
        String updateId = (String)data.get("updateId");
        
        CIrelationmap cirelationmap = null;
        List<CIrelationmap> cirelationmapList = new ArrayList<CIrelationmap>(); 

        if(childrenYn){
            List<Classancestor> sourceclassList = classancestorRepository.findByOrgIdAndAncestor(orgId, sourceclass);
            List<Classancestor> targetclassList = classancestorRepository.findByOrgIdAndAncestor(orgId, targetclass);
            
            for(Classancestor sourceclassChild : sourceclassList) { 
                for(Classancestor targetclassChild : targetclassList) {
                    cirelationmap = new CIrelationmap();
                    cirelationmap.setOrgId(orgId);
                    cirelationmap.setSourceclass(sourceclassChild.getClassstructureId());
                    cirelationmap.setTargetclass(targetclassChild.getClassstructureId());
                    cirelationmap.setRelationname(relationname);
                    cirelationmap.setCreateId(createId);
                    cirelationmap.setUpdateId(updateId);
                    cirelationmapList.add(cirelationmap); 

                    cirelationmap = new CIrelationmap();
                    cirelationmap.setOrgId(orgId);
                    cirelationmap.setSourceclass(targetclassChild.getClassstructureId());
                    cirelationmap.setTargetclass(sourceclassChild.getClassstructureId());
                    cirelationmap.setRelationname(relationname);
                    cirelationmap.setCreateId(createId);
                    cirelationmap.setUpdateId(updateId);
                    cirelationmapList.add(cirelationmap); 
                }
            } 
            
            cirelationmapRepository.saveAll(cirelationmapList);
        } else {
            cirelationmap = new CIrelationmap();
            cirelationmap.setOrgId(orgId);
            cirelationmap.setSourceclass(sourceclass);
            cirelationmap.setTargetclass(targetclass);
            cirelationmap.setRelationname(relationname);
            cirelationmap.setCreateId(createId);
            cirelationmap.setUpdateId(updateId);
            cirelationmapList.add(cirelationmap);

            cirelationmap = new CIrelationmap();
            cirelationmap.setOrgId(orgId);
            cirelationmap.setSourceclass(targetclass);
            cirelationmap.setTargetclass(sourceclass);
            cirelationmap.setRelationname(relationname);
            cirelationmap.setCreateId(createId);
            cirelationmap.setUpdateId(updateId);
            cirelationmapList.add(cirelationmap);
            
            cirelationmapRepository.saveAll(cirelationmapList);
        }
    }

    /**
     * name : getItInfraSituation
     * description : 분류별 인프라 현황
     * @param : String time
     * @return : ResponseEntity
     */
    public Map<String, Object> getItInfraSituation(String cTime) {

        Map<String,Object> result = new HashMap<>();
        List<List<Map<String, Object>>> infraList = null;
        for(String ancestor : INFRA_ANCESTOR) {
            infraList = new ArrayList<>();
            infraList.add(ciMapper.getItInfraSituation(ancestor));
            result.put(ancestor + "_total", ciMapper.infraTot(ancestor, cTime));
            result.put(ancestor, infraList);
        }
        return result;
    }

    /**
     * name : getScanCI
     * description : IT 인프라 SacnCI 현황
     * @param : null
     * @return : ResponseEntity
     */
    public Map<String, Object> getScanCI() {

        Map<String,Object> result = new HashMap<>();
        for(String scancitype : SCANCI_TYPES) {
            result.put(scancitype, ciMapper.scanCI(scancitype));
        }
        return result;
    }

}
