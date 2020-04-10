/**
* 분류 비지니스 서비스 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.util.StringUtils;

@Service
public class ClassificationService {

    @Resource
    private ClassificationMapper classificationMapper;

    @Resource
    private ClassancestorRepository classancestorRepository;

    @Resource
    private ClassstructureRepository classstructureRepository;

    public void insertAncestorData (Classstructure data){
        
        classancestorRepository.saveAll(makeAncestorData(data));
    }

    public List<Classancestor> makeAncestorData (Classstructure data){

        String orgId = data.getOrgId(); 
        String classstructureId = data.getClassstructureId();
        String hierarchypath = data.getHierarchypath(); 
        String createId = data.getCreateId();

        List<Classancestor> rtnList = new ArrayList<Classancestor>();
        String[] pathList = (hierarchypath.substring(1)).split("/");        
        
        for(String path : pathList){
            
            Classancestor classancestor = new Classancestor();
            classancestor.setOrgId(orgId);
            classancestor.setAncestor(path);
            classancestor.setClassstructureId(classstructureId);
            classancestor.setCreateId(createId);    
            rtnList.add(classancestor);
        }

        return rtnList;
    }

    public Classstructure property(String ancestor) {

        return classificationMapper.property(ancestor);

    }

    public void updateAncestorData (Classstructure data){
        Classstructure oldData = classstructureRepository.findById(data.getId()).get();

        if(!StringUtils.nvl(data.getParent(),"").equals(StringUtils.nvl(oldData.getParent(),""))){
            String orgId = data.getOrgId();
            String classstructureId = data.getClassstructureId();
            String hierarchypath = data.getHierarchypath();

            List<Classancestor> refAncestorList = makeAncestorData(data);
            
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("orgId", orgId); 
            param.put("classstructureId", classstructureId); 
            
            List<Map<String, Object>> deleteAncestorlist = classificationMapper.deleteAncestorlist(param);
            
            for(Map<String, Object> ancestor : deleteAncestorlist){
                
                String path = (String)ancestor.get("path");
                String targetClassstructureId = (String)ancestor.get("classstructure_id");
                
                if(!StringUtils.isNull(path)){

                    String[] targetList = (path.substring(1)).split("/");
                    
                    for(String targetAncestor : targetList){
                        
                        param = new HashMap<String, Object>();
                        param.put("orgId", orgId);
                        param.put("ancestor", targetAncestor);
                        param.put("classstructureId", targetClassstructureId);

                        classificationMapper.deleteAncestor(param);
                    }

                    List<Classancestor> newAncestorList = new ArrayList<Classancestor>();
                    
                    for(Classancestor refAncestor : refAncestorList){

                        if(!refAncestor.getAncestor().equals(classstructureId)){
                            
                            Classancestor newClassancestor = new Classancestor();

                            newClassancestor.setOrgId(orgId);
                            newClassancestor.setAncestor(refAncestor.getAncestor());
                            newClassancestor.setClassstructureId(targetClassstructureId);
                            newClassancestor.setCreateId(refAncestor.getCreateId());    
                            newAncestorList.add(newClassancestor);                        
                        }
                    }
                    
                    classancestorRepository.saveAll(newAncestorList);
                    
                    param = new HashMap<String, Object>();

                    param.put("orgId", orgId);
                    param.put("classstructureId", targetClassstructureId);
                    param.put(ESConstants.USER_FILTER_KEY, "1=1");
                    
                    List<Classstructure> classstructureList = classificationMapper.list(param);

                    Classstructure classstructure = classstructureList.get(0);

                    String targetPath = classstructure.getHierarchypath();

                    int cutIdx = targetPath.indexOf(classstructureId);

                    targetPath = hierarchypath+targetPath.substring(cutIdx+classstructureId.length());

                    classstructure.setHierarchypath(targetPath); 
                    
                    classstructureRepository.save(classstructure);
                }
            }
        }
    }    
}
