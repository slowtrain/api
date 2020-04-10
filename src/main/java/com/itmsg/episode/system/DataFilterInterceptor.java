package com.itmsg.episode.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itmsg.episode.app.system.user.UserFilter;
import com.itmsg.episode.app.system.user.UserFilterRepository;
import com.itmsg.episode.app.system.user.UserMapper;
import com.itmsg.episode.security.JwtTokenUtil;
import com.itmsg.episode.security.JwtUser;

@Component
public class DataFilterInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        Object userAttr = request.getAttribute(ESConstants.USER_DETAILS_KEY);
        if (userAttr != null && handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            DataFilterEnabled filterEnabled = method.getMethodAnnotation(DataFilterEnabled.class);
            if (filterEnabled != null) {
                UserDetails userDetails = (UserDetails) userAttr;
                ESDataFilter datafilter = filterEnabled.filter();
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", userDetails.getUsername());
                param.put("datafilterId", datafilter.name());

                List<UserFilter> userFilters = userMapper.getUserFilter(param);
                if (!userFilters.isEmpty()) {
                    if (datafilter.type().equals(ESDataFilter.DATABASE)) {
                        String whereClauses = "";
                        int filterSize = userFilters.size();
                        String whereClause = null;
                        for (int i = 0; i < filterSize; i++) {
                            whereClause = userFilters.get(i).getWhereClause();
                            whereClause = whereClause.replaceAll("&username&", "'" + userDetails.getUsername() + "'");
                            whereClause = whereClause.replaceAll("&org&","'" + ((JwtUser) userDetails).getOrgId() + "'");
                            whereClauses += whereClause;
                            if (i < (filterSize - 1)) {
                                whereClauses += " or ";
                            }
                        }
                        request.setAttribute(ESConstants.USER_FILTER_KEY, whereClauses);
                    } else if (datafilter.type().equals(ESDataFilter.API)) {
                        JSONArray jsonArray = new JSONArray();
                        for(UserFilter userFilter:userFilters){
                            jsonArray.put(new JSONObject(userFilter.getWhereClause()));
                        }     
                        request.setAttribute(ESConstants.USER_FILTER_KEY,jsonArray);
                    }
                }

                /*
                Optional<UserFilter> userFilter =  this.userFilterRepository.findByUsernameAndDatafilterId(userDetails.getUsername(), datafilter.name());
                if(userFilter.isPresent()){
                    String whereClause = userFilter.get().getWhereClause();
                    whereClause = whereClause.replaceAll("&username&","'"+userDetails.getUsername()+"'");
                    if(datafilter.type().equals(ESDataFilter.DATABASE)){
                        request.setAttribute(ESConstants.USER_FILTER_KEY,whereClause);
                    }else if (datafilter.type().equals(ESDataFilter.API)) {
                        JSONObject json = new JSONObject(whereClause);
                        request.setAttribute(ESConstants.USER_FILTER_KEY,json);
                    }
                }
                */
            }
        }

        return true;
    }
    


    /*
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      
    }
    */

    /*
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
    */

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private UserFilterRepository userFilterRepository;

    @Resource
    private ListableBeanFactory listableBeanFactory;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserMapper userMapper;
    
    @Resource(name="jwtUserDetailsService")
    private UserDetailsService userDetailsService;

}
