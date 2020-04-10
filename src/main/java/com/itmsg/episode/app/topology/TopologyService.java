/**
* Service of TOPOLOGY
* @author   백승우
* @since    2019.09.01
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.01   백승우   최초 생성
*/
package com.itmsg.episode.app.topology;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TopologyService {
    @Resource
    private TopologyMapper topologyMapper;

    public String convertClassParam(Map<String, Object> param){
        return new ParamConverter()
                    .convert(param,"RUN")
                    .convert(param,"INCLUDE")
                    .convert(param,"VIRTUALIZE")
                    .convert(param,"CONNECT")
                    .convert(param,"DEPEND")
                    .convert(param,"CLUSTER")
                    .getConvertedParams();
    }

    public String convertNodesParam(List<String> nodes){
        StringBuilder params = new StringBuilder();
        for(int i=0; i<nodes.size(); i++){
            if(i>0){
                params.append(",");
            }
            params.append("'").append(nodes.get(i)).append("'");
        }
        return params.toString();
    }


    class ParamConverter {
        private StringBuilder params;
        ParamConverter(){
            this.params = new StringBuilder();
        }
        ParamConverter convert(Map<String, Object> param, String applyClass){
            if(param.get(applyClass)!=null && ((String)param.get(applyClass)).equals("true")){
                if(params.length()>0){
                    params.append(",");
                }
                params.append("'").append(applyClass).append("'");
            }
            return this;
        }
        String getConvertedParams(){
            return params.toString();
        }
    }
}
