package com.itmsg.episode.common.files;

import javax.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Resource(name="sqlSessionFactory")
public interface AttachmentMapper {
    
    String getAutonum();    

}