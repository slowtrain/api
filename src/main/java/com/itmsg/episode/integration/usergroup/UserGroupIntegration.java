package com.itmsg.episode.integration.usergroup;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.itmsg.episode.integration.Integration;

@WebService
public interface UserGroupIntegration {
    @WebMethod
    public void syncUserGroup(@WebParam(name="datas") List<Integration> datas);
}