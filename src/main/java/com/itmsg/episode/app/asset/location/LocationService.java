/**
* 위치 비지니스 서비스 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service
public class LocationService {

    @Resource
    private LocationMapper locationMapper;

    @Resource
    private LocancestorRepository locancestorRepository;

    @Resource
    private LocationRepository locationRepository;


    public void insertAncestorData (Location data){
        
        locancestorRepository.saveAll(makeAncestorData(data));
    }

    public List<Locancestor> makeAncestorData (Location data){

        String orgId = data.getOrgId(); 
        String locationId = data.getLocationId();
        String hierarchypath = data.getHierarchypath(); 
        String createId = data.getCreateId();

        List<Locancestor> rtnList = new ArrayList<Locancestor>();
        String[] pathList = (hierarchypath.substring(1)).split("/");        
        
        for(String path : pathList){
            
            Locancestor locancestor = new Locancestor();
            locancestor.setOrgId(orgId);
            locancestor.setAncestor(path);
            locancestor.setLocationId(locationId);
            locancestor.setCreateId(createId);    
            rtnList.add(locancestor);
        }

        return rtnList;
    }

}
