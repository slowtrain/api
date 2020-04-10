package com.itmsg.episode.integration.sappo;

import javax.annotation.Resource;

import com.itmsg.episode.integration.sappo.jaxws.SyncSappo;
import com.itmsg.episode.integration.sappo.jaxws.SyncSappoResponse;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@Component
public class SappoIntegrationEndpoint{

    public static final String NAMESPACE_URI = "http://sappo.integration.episode.itmsg.com/";
	public static final String PORT_NAME = "SappoIntegrationPort";
    public static final String SERVICE_NAME = "SappoIntegration";

	@Resource
	SappoIntegration sappoIntegration;
	
	@PayloadRoot(namespace = SappoIntegrationEndpoint.NAMESPACE_URI, localPart = "syncSappo")
	@ResponsePayload
	public SyncSappoResponse syncSappo(@RequestPayload SyncSappo request) {
		
		SyncSappoResponse response = new SyncSappoResponse();
		//response.setReturn(sappoIntegration.syncSappo(request.getDatas()));
		sappoIntegration.syncSappo(request.getDatas());
		return response;
	}

}
