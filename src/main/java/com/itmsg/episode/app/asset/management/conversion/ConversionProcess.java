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

import org.springframework.stereotype.Component;

@Component
public abstract class ConversionProcess {
    protected Map<String, String> parameter;
    public void setParameter(Map<String, String> parameter){
        this.parameter = parameter;
    };
    public void excuteProcess(){};
}