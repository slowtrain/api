package com.itmsg.episode.app.system.scheduler;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SchedulerMapper {
    List<Scheduler> list(Map<String,Object> param);
    void deleteHistory(String schedulerId);
    void deleteAction(String schedulerId);
}
