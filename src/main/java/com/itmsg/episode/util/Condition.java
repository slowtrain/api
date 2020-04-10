package com.itmsg.episode.util;

import javax.annotation.Resource;

import com.itmsg.episode.system.SystemCommonMapper;

import org.springframework.stereotype.Component;

// @UtilityClass
@Component
public class Condition {

    @Resource
    private SystemCommonMapper systemCommonMapper;

    Boolean isTrue(String object,Long id,String sql){
        return systemCommonMapper.isTrue(object,id,sql);
    }

}