package com.itmsg.episode.app.system.workflow;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface WorkflowMapper {
    Boolean isTrue(@Param("object") String object,@Param("id") Long id,@Param("sql") String sql);
    Integer getMaxRevision(@Param("objectId") String objectId);
    WFInstance getWFInstance(@Param("objectId") String objectId,@Param("uid") Long uid);
    Boolean isAssigned(@Param("instanceUid") Long instanceUid,@Param("username") String username);

    List<Map<String,Object>> getWFAssignment(@Param("username") String username,@Param("available") Boolean available);
    Map<String,Object> currentWFNode(@Param("objectId") String objectId,@Param("uid") Long uid);
    List<Map<String,Object>> currentWFConnector(@Param("objectId") String objectId,@Param("uid") Long uid);
}
