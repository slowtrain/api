package com.itmsg.episode.system;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SystemCommonMapper {
    
	List<Map<String,Object>> getNotifyObject( String notiType);
	
	Boolean isTrue(@Param("object") String object,@Param("id") Long id,@Param("sql") String sql);

    Map<String,Object> findOne(@Param("objectId") String objectId,@Param("uid") Long uid);
}
