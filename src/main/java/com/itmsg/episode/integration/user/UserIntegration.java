package com.itmsg.episode.integration.user;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.itmsg.episode.integration.Integration;

@WebService
public interface UserIntegration{
    @WebMethod
    public void syncUser(@WebParam(name="datas") List<Integration> datas);
}