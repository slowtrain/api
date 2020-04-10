package com.itmsg.episode.integration.user;

import javax.annotation.Resource;

import com.itmsg.episode.integration.user.jaxws.SyncUser;
import com.itmsg.episode.integration.user.jaxws.SyncUserResponse;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@Component
public class UserIntegrationEndpoint{

	public static final String NAMESPACE_URI = "http://user.integration.episode.itmsg.com/";
	public static final String PORT_NAME = "UserIntegrationPort";
	public static final String SERVICE_NAME = "UserIntegration";

	@Resource
	UserIntegration userIntegration;
	
	@PayloadRoot(namespace = UserIntegrationEndpoint.NAMESPACE_URI, localPart = "syncUser")
	@ResponsePayload
	public SyncUserResponse syncUser(@RequestPayload SyncUser request) {
		
		SyncUserResponse response = new SyncUserResponse();
		//response.setReturn(userIntegration.syncUser(request.getCredential(), request.getDatas()));
		userIntegration.syncUser(request.getDatas());
		return response;
	}

}
