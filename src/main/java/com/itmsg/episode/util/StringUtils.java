package com.itmsg.episode.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.itmsg.episode.app.system.classification.Classstructure;

// import lombok.experimental.UtilityClass;

// @UtilityClass
public class StringUtils {

    public static int parseInt(String value) {
        if (value == null || value.length() == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String nvl(String val, String str){
        String rtn = val;
        if(val == null || val.length() == 0){
            rtn = str;
        }

        return rtn;
    }
     
    public static Boolean isNull(String val){

        if(val == null || val.length() == 0){
            return true;
        }

        return false;
    }

    public static String changeColName(Class clss, String colNm){

        Class c = clss;
        
        Field[] fields = c.getDeclaredFields();
        
        for(Field field : fields){
            
            field.setAccessible(true);
            
            if(field.getAnnotation(Column.class) instanceof Column){
                
                if(field.getName().equals(colNm)){
                    
                    return field.getAnnotation(Column.class).name().toString();        
                }
            }    
        }
        return "";
    }

    public static String changeFieldName(Class clss, String fieldNm){

        Class c = clss;
        
        Field[] fields = c.getDeclaredFields();
        
        for(Field field : fields){
            
            field.setAccessible(true);
            
            if(field.getAnnotation(Column.class) instanceof Column){
                if((field.getAnnotation(Column.class).name().toString().toLowerCase()).equals(fieldNm.toLowerCase())){                    
                    return field.getName();        
                }
            }    
        }
        return "";
    }

    public static Map<String, String> getFieldNames(Class clss){
        Map<String, String> rtnMap = new HashMap<String, String>();
        Class c = clss;
        
        Field[] fields = c.getDeclaredFields();
        
        for(Field field : fields){
            
            field.setAccessible(true);
            
            if(field.getAnnotation(Column.class) instanceof Column){
                rtnMap.put(field.getAnnotation(Column.class).name().toString().toLowerCase(), field.getName());          
            }    
        }
        return rtnMap;
    }
}
