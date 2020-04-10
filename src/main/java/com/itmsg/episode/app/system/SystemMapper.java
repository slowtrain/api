package com.itmsg.episode.app.system;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SystemMapper {
    
}
