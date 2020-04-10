/**
* 메뉴 비지니스 서비스 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.navigator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.util.StringUtils;

@Service
public class NavigatorService {

    @Resource
    private NavigatorMapper navigatorMapper;

    @Resource
    private NavAncestorRepository navAncestorRepository;

    @Resource
    private NavigatorRepository navigatorRepository;

    public void insertAncestorData (Navigator data){
        
        navAncestorRepository.saveAll(makeAncestorData(data));
    }

    public List<NavAncestor> makeAncestorData (Navigator data){

        String orgId = data.getOrgId(); 
        String navigatorId = data.getNavigatorId();
        String hierarchypath = data.getHierarchypath(); 
        String createId = data.getCreateId();

        List<NavAncestor> rtnList = new ArrayList<NavAncestor>();
        String[] pathList = (hierarchypath.substring(1)).split("/");        
        
        for(String path : pathList){
            
            NavAncestor navAncestor = new NavAncestor();
            navAncestor.setOrgId(orgId);
            navAncestor.setAncestor(path);
            navAncestor.setNavigatorId(navigatorId);
            navAncestor.setCreateId(createId);    
            rtnList.add(navAncestor);
        }

        return rtnList;
    }

    public void updateAncestorData (Navigator data){
        Navigator oldData = navigatorRepository.findById(data.getId()).get();

        if(!StringUtils.nvl(data.getParent(),"").equals(StringUtils.nvl(oldData.getParent(),""))){
            String orgId = data.getOrgId();
            String navigatorId = data.getNavigatorId();
            String hierarchypath = data.getHierarchypath();

            List<NavAncestor> refAncestorList = makeAncestorData(data);
            
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("orgId", orgId); 
            param.put("navigatorId", navigatorId); 
            
            List<Map<String, Object>> deleteAncestorList = navigatorMapper.deleteAncestorList(param);
            
            for(Map<String, Object> ancestor : deleteAncestorList){
                
                String path = (String)ancestor.get("path");
                String targetNavigatorId = (String)ancestor.get("navigator_id");
                
                if(!StringUtils.isNull(path)){

                    String[] targetList = (path.substring(1)).split("/");
                    
                    for(String targetAncestor : targetList){
                        
                        param = new HashMap<String, Object>();
                        param.put("orgId", orgId);
                        param.put("ancestor", targetAncestor);
                        param.put("navigatorId", targetNavigatorId);

                        navigatorMapper.deleteAncestor(param);
                    }

                    List<NavAncestor> newAncestorList = new ArrayList<NavAncestor>();
                    
                    for(NavAncestor refAncestor : refAncestorList){

                        if(!refAncestor.getAncestor().equals(navigatorId)){
                            
                            NavAncestor newNavAncestor = new NavAncestor();

                            newNavAncestor.setOrgId(orgId);
                            newNavAncestor.setAncestor(refAncestor.getAncestor());
                            newNavAncestor.setNavigatorId(targetNavigatorId);
                            newNavAncestor.setCreateId(refAncestor.getCreateId());    
                            newAncestorList.add(newNavAncestor);                        
                        }
                    }
                    
                    navAncestorRepository.saveAll(newAncestorList);
                    
                    param = new HashMap<String, Object>();

                    param.put("orgId", orgId);
                    param.put("navigatorId", targetNavigatorId);
                    
                    List<Navigator> navigatorList = navigatorMapper.list(param);

                    Navigator navigator = navigatorList.get(0);

                    String targetPath = navigator.getHierarchypath();

                    int cutIdx = targetPath.indexOf(navigatorId);

                    targetPath = hierarchypath+targetPath.substring(cutIdx+navigatorId.length());

                    navigator.setHierarchypath(targetPath); 
                    
                    navigatorRepository.save(navigator);
                }
            }
        }    
    }
    
    public List<Map<String, Object>> mainNaviList (Map<String, Object> param){
        List<Map<String, Object>> mainNaviList = navigatorMapper.mainNaviList(param);                    
        return makeTreeData(mainNaviList, "");
    }

    public List<Map<String, Object>> makeTreeData (List<Map<String, Object>>dataList, String rootVal) {
        
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
      
        for (Map<String, Object> data : dataList) {
            
            String dataVal = (String)data.get("navigator_id");
            String name = (String)data.get("name");
            String parentDataVal = (String)data.get("parent");
            String url = (String)data.get("url");
            String icon = (String)data.get("icon");
            
            if (parentDataVal.equals(rootVal)) {
                
                // if (StringUtils.isNull(parentDataVal)) {
                //     Map<String, Object> tempData = new HashMap<>();
                //     tempData.put("navigatorId", dataVal);
                //     tempData.put("title", true);
                //     tempData.put("name", name);
                //     tempData.put("class", icon);
                //     rtnList.add(tempData);
                // }

                Map<String, Object> tempData = new HashMap<>();

                tempData.put("navigatorId", dataVal);
                tempData.put("name", name);
                tempData.put("url", url);
                tempData.put("icon", icon);
                
                List<Map<String, Object>> children = makeTreeData(dataList, dataVal);

                if(children.size()>0){
                    tempData.put("children", children);            
                }    
                
                // if (!StringUtils.isNull(parentDataVal)) {
                    rtnList.add(tempData);
                // } else {
                    for(Map<String, Object> child : children) {
                        // rtnList.add(child);
                    }
                // }    
            }
        }
        return rtnList;
    }
}
