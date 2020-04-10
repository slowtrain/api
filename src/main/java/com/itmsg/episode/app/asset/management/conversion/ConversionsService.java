/**
* IT 자원  비지니스 서비스 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.itmsg.episode.config.AsyncConfig;
import com.itmsg.episode.system.ESConversionProcess;
import com.itmsg.episode.util.StringUtils;

    
@Service
public class ConversionsService {

    @Resource
    private ConversionRepository conversionRepository;

    @Resource
    private ApplicationContext applicationContext;
        
    public List<Map<String, String>> processList() {
        List<Map<String, String>> processList = new ArrayList<Map<String, String>>();

        Map<String, String> process = null;
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ESConversionProcess.class);
        Iterator<String> keys = beans.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            Class clazz = beans.get(key).getClass();
            ESConversionProcess esConversionProcess =(ESConversionProcess)clazz.getAnnotation(ESConversionProcess.class);
            
            process = new HashMap<String,String>();
            process.put("key", key);
            process.put("value", key);
            
            processList.add(process);
        }       
        return processList;
    }

    public void conversion(Conversion oldData, Conversion newData){
        String oldConverterId = oldData == null ? "" : StringUtils.nvl(oldData.getConverterId(), "");
        String newConverterId = StringUtils.nvl(newData.getConverterId(), "");
        String oldProcess = oldData == null ? "" : StringUtils.nvl(oldData.getConversionprocess(), ""); 
        String newProcess = StringUtils.nvl(newData.getConversionprocess(), ""); 
        if((!oldConverterId.equals(newConverterId) || !oldProcess.equals(newProcess)) && !StringUtils.isNull(newConverterId) && !StringUtils.isNull(newProcess)){
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put("columnname", newData.getColumnname());
            parameter.put("converterId", newData.getConverterId());
            parameter.put("classstructureId", newData.getClassstructureId());
            excuteProcess(newProcess, parameter);
        }
    }

    public void conversion(List<Converter> dataList){
        List<String> converterlist = new ArrayList<String>();
        for(Converter data : dataList){
            if(converterlist.indexOf(data.getConverterId()) == -1){
                converterlist.add(data.getConverterId());
            }
        }
        for(String converterId : converterlist){
            List<Conversion> conversionList = conversionRepository.findByConverterId(converterId);
            for(Conversion conversion : conversionList) {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("columnname", conversion.getColumnname());
                parameter.put("converterId", conversion.getConverterId());
                parameter.put("classstructureId", conversion.getClassstructureId());
                if(!StringUtils.isNull(conversion.getConversionprocess())){
                    excuteProcess(conversion.getConversionprocess(), parameter);
                }
            }
        }
    }

    @Async(AsyncConfig.CONVERSION_EXECUTOR)
    public void excuteProcess (String process, Map<String, String> parameter) {
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ESConversionProcess.class);
        ConversionProcess conversionProcess = (ConversionProcess)beans.get(process);  
        conversionProcess.setParameter(parameter);
        conversionProcess.excuteProcess();
    }
}
