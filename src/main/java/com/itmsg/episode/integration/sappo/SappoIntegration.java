package com.itmsg.episode.integration.sappo;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.itmsg.episode.integration.Integration;

@WebService
public interface SappoIntegration {
    @WebMethod
    public void syncSappo(@WebParam(name="datas") List<Integration> datas);
}