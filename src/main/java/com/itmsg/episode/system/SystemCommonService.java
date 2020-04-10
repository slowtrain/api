package com.itmsg.episode.system;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

// import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import com.itmsg.episode.app.system.classification.Classstructure;
import com.itmsg.episode.app.system.domain.Domain;
import com.itmsg.episode.app.system.domain.DomainRepository;
import com.itmsg.episode.util.StringUtils;


@Service
public class SystemCommonService {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SystemCommonMapper systemCommonMapper;

    @Resource
    private DomainRepository domainRepository;
    
    // @Getter
    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getAutonum (String orgId, String tableNm, String columnNm){
        String rtn = null;
        Query query = em.createNativeQuery("select getautonum_fn(:orgId,:tableNm,:columnNm)");
        query.setParameter("orgId", orgId);
        query.setParameter("tableNm", tableNm);
        query.setParameter("columnNm", columnNm);
        rtn = (String)query.getSingleResult();
        return rtn;
    }

    public String getDomainnm (String domainId, String key){
        String rtn = null;
        Query query = em.createNativeQuery("select getdomainnm_fn(:domainId,:key)");
        query.setParameter("domainId", domainId);
        query.setParameter("key", key);
        rtn = (String)query.getSingleResult();
        return rtn;
    }

    public String getusername (String username){
        String rtn = null;
        Query query = em.createNativeQuery("select getusername_fn(:username)");
        query.setParameter("username", username);
        rtn = (String)query.getSingleResult();
        return rtn;
    }
	
	public Map<String,Object> findOne(String objectId, Long uid) {
        return systemCommonMapper.findOne(objectId, uid);
    }
        
    public List<Map<String,Object>> getNotifyObject (String notiType){
        return systemCommonMapper.getNotifyObject(notiType);
    }

    public List<Domain> getDomain(String domainId){
        return domainRepository.findByDomainId(domainId,new Sort(Sort.Direction.ASC,"seq"));
    }

    public List<Map<String,String>> appOptionList(String appId){

        List<Map<String,String>>  options = new ArrayList<Map<String,String>>();
        
        try{

            Map<String,String> option = null;

            Map<String,Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);
            Iterator<String> keys = beans.keySet().iterator();

            while(keys.hasNext()){
                String key = keys.next();
                Field[] fields = beans.get(key).getClass().getFields();
                for(Field field:fields){
                    if(field.isAnnotationPresent(ESApplicationOption.class)){
                        ESApplicationOption esOption = field.getAnnotation(ESApplicationOption.class);
                        
                        if(appId != null && !esOption.application().equals(appId)){
                            continue;
                        }

                        option = new HashMap<String,String>();
                        option.put("appId", esOption.application());
                        option.put("option", field.get(null).toString());
                        option.put("description", esOption.description());
                        options.add(option);

                    }
                }
            }
        }catch (IllegalAccessException iae){
            return new ArrayList<Map<String,String>>();
        }
      
        return options;
    }
	
	 public List<Map<String,String>> tableList(String entityName)  {
        List<Map<String,String>>  tables = new ArrayList<Map<String,String>>();
        try {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(Table.class));
            Set<BeanDefinition> beans= scanner.findCandidateComponents("com.itmsg.episode.app");
            Map<String, String> table = null;
            String className = null;
            Class tableClass = null;
            Table tableAnnotation = null;
            for(BeanDefinition bean:beans){
                table = new HashMap<String, String>();
                className = bean.getBeanClassName();
                tableClass = Class.forName(className);
                tableAnnotation = (Table)tableClass.getAnnotation(Table.class);

                if(entityName != null){
                    if(entityName.equals(tableAnnotation.name())){
                        table.put("tableName", tableAnnotation.name());
                        table.put("className", className);
                        tables.add(table);
                        break;
                    }
                } else {
                    table.put("tableName", tableAnnotation.name());
                    table.put("className", className);
                    tables.add(table);
                }

             
            }
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }      
        return tables;
    }

    public List<Map<String, Object>> makeTreeData (List<Map<String, Object>>dataList, String rootVal, String parentColNm, String childColNm, String textColNm) {
        
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
      
        for (Map<String, Object> data : dataList) {
                      
            String dataVal = (String)data.get(childColNm);
            if (!StringUtils.isNull(textColNm)) {
                dataVal = (String)data.get(textColNm);
            }
            String parentDataVal = (String)data.get(parentColNm);
            Map<String, Object> tempData = new HashMap<>();            
            if (StringUtils.nvl(parentDataVal, "").equals(StringUtils.nvl(rootVal, ""))) {
                tempData.put("id", data.get("id"));
                tempData.put("label", dataVal);
                tempData.put("data", data);
                tempData.put("children", makeTreeData(dataList, (String)data.get(childColNm), parentColNm, childColNm, textColNm));
                ((Map<String, Object>)tempData.get("data")).put("cnt", ((List<Map<String, Object>>)tempData.get("children")).size());
            
                for (Map<String, Object> child : (List<Map<String, Object>>)tempData.get("children")) {
                    int cnt = (int)((Map<String, Object>)child.get("data")).get("cnt");   
                    int oldCnt = (int)(((Map<String, Object>)tempData.get("data")).get("cnt"));                          
                    ((Map<String, Object>)tempData.get("data")).put("cnt", oldCnt + cnt);
                }
                rtnList.add(tempData);
            }
        }
        return rtnList;
    }
}
