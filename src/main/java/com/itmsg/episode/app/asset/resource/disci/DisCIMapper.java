package com.itmsg.episode.app.asset.resource.disci;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Mapper;

@Mapper
@Resource(name="sqlSessionFactory")
public interface DisCIMapper {
    List<DisCI> list(Map<String, Object> param);
    int listTot(Map<String, Object> param);
    TreeMap<String, Object> disciExtNmData(Map<String, Object> param);
    List<DisCIrelation> discirelationList(Map<String, Object> param);

    void insertCIRelationHisActive();
    void updateCIRelationsActive();
    
    void insertCIRelationHisInactive();
    void updateCIRelationsInactive();

    void insertRPASystem();
    void insertDisCIMapToCI();

    void addCIRelations();

    void updateCISW(DisCI disCI);

    void deleteCISW(DisCI disCI);

    String getClassstructure(DisCI disCI);

    List<DisCI> getPromoteTarget(String dataSource);

    List<DisCI> getDisCIByCIid(String ciId);

    List<DisCI> getSameHostAndPromoted(DisCI disCI);

    // List<Map<String, Object>> emsDisCI(String discitype);
    Map<String, Object> emsDisCI(String discitype);

}