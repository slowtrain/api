package com.itmsg.episode.integration;

import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Resource;

import javax.xml.ws.BindingProvider;

import com.itmsg.episode.security.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.soap11.Soap11Header;
import org.springframework.ws.transport.HeadersAwareReceiverWebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpComponentsConnection;
import org.springframework.ws.transport.http.HttpUrlConnection;
import org.w3c.dom.Element;

public class ESEndpointInterceptor extends EndpointInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    @Resource
    private AuthenticationManager authenticationManagerBean;
    */

    @Override
    public boolean understands(Element header) {
        logger.debug("understands !!!!!");

        return super.understands(header);
    }

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        logger.debug("handleRequest !!!!! ");    
        
        /*
        SaajSoapMessage message = (SaajSoapMessage)messageContext.getRequest();
        String username =message.getSaajMessage().getMimeHeaders().getHeader(BindingProvider.USERNAME_PROPERTY)[0];        
        String password =message.getSaajMessage().getMimeHeaders().getHeader(BindingProvider.PASSWORD_PROPERTY)[0];
        */

        return super.handleRequest(messageContext, endpoint);
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        logger.debug("handleResponse !!!!!");
        return super.handleResponse(messageContext, endpoint);
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        logger.debug("handleFault !!!!!");
        return super.handleFault(messageContext, endpoint);
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        logger.debug("afterCompletion !!!!!");
        super.afterCompletion(messageContext, endpoint, ex);
    }

  
}