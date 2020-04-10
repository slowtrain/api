/**
* 위치  SQL Map 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Mapper
@Resource(name="sqlSessionFactory")
public interface LocationMapper {
    List<Location> list(Map<String,Object> param);
    List<Map<String, Object>> treeList(Map<String,Object> param);
    List<Map<String, Object>> deleteAncestorlist(Map<String,Object> param);
    void deleteAncestor(Map<String,Object> param);
}

