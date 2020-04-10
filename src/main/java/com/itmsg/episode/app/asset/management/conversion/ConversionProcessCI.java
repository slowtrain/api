/**
* 변환 callback 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.itmsg.episode.app.asset.resource.disci.DisCI;
import com.itmsg.episode.system.ESConversionProcess;
import com.itmsg.episode.util.StringUtils;

import org.springframework.stereotype.Component;

@Component("ConversionProcessCI")
@ESConversionProcess(description="conversion ci Process")
public class ConversionProcessCI extends ConversionProcess {

    @Resource
    private ConversionMapper conversionMapper;

    @Override
    public void excuteProcess() {
        String columnname = parameter.get("columnname");
        columnname = StringUtils.changeColName(DisCI.class, columnname);
        if(!StringUtils.isNull(columnname)){
            parameter.put("columnname", columnname);
            List<Map<String, String>> convertedvalueList = conversionMapper.convertedvalueList(parameter);
            for(Map<String, String> convertedvalue : convertedvalueList){
                parameter.put("convertedvalue", convertedvalue.get("convertedvalue"));
                conversionMapper.convertCI(parameter);        
            }
        }
    }    
}