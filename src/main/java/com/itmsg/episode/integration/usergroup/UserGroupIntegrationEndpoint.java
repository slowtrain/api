package com.itmsg.episode.integration.usergroup;

import javax.annotation.Resource;

import com.itmsg.episode.integration.usergroup.jaxws.SyncUserGroup;
import com.itmsg.episode.integration.usergroup.jaxws.SyncUserGroupResponse;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Component
public class UserGroupIntegrationEndpoint{
	
    public static final String NAMESPACE_URI = "http://usergroup.integration.episode.itmsg.com/";
	public static final String PORT_NAME = "UserGroupIntegrationPort";
	public static final String SERVICE_NAME = "UserGroupIntegration";

	@Resource
	UserGroupIntegration userGroupIntegration;

	@PayloadRoot(namespace = UserGroupIntegrationEndpoint.NAMESPACE_URI, localPart = "syncUserGroup")
	@ResponsePayload
	public SyncUserGroupResponse syncUser(@RequestPayload SyncUserGroup request) {
		SyncUserGroupResponse response = new SyncUserGroupResponse();
		userGroupIntegration.syncUserGroup(request.getDatas());
		return response;
	}

}
