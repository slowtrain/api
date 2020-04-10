package com.itmsg.episode.config;

import com.itmsg.episode.integration.ESEndpointInterceptor;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean
	public ESEndpointInterceptor esEndpointInterceptor(){
		return new ESEndpointInterceptor();
	}


    @Bean(name = "user")
	public SimpleWsdl11Definition userWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/ws/UserIntegration.wsdl"));
		return wsdl11Definition;
	}

	
	@Bean(name = "usergroup")
	public SimpleWsdl11Definition groupWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/ws/UserGroupIntegration.wsdl"));
		return wsdl11Definition;
	}

	@Bean(name = "sappo")
	public SimpleWsdl11Definition sappoWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/ws/SappoIntegration.wsdl"));
		return wsdl11Definition;
	}

	
	
	/*
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(esEndpointInterceptor());
		super.addInterceptors(interceptors);
	}
	*/


	/*
	@Bean(name = "user")
	public DefaultWsdl11Definition userWsdl11Definition(XsdSchema userSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName(USER_PORT_NAME);
		wsdl11Definition.setLocationUri(USER_LOCATION_URI);
		wsdl11Definition.setTargetNamespace(USER_NAMESPACE_URI);
		wsdl11Definition.setSchema(userSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema userSchema() {
		return new SimpleXsdSchema(new ClassPathResource("ws/UserIntegration_schema1.xsd"));
	}
	*/

	
}
