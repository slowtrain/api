package com.itmsg.episode.app.system.signup;

import org.apache.ibatis.annotations.Mapper;
import javax.annotation.Resource;
import java.util.Map;

@Mapper
@Resource(name="sqlSessionFactory")
public interface SignupMapper {
    Map<String, Object> getUserInfo(Map<String, Object> param);
}