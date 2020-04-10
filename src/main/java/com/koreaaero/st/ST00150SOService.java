
package com.koreaaero.st;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
// @WebServiceClient(name = "ST00150_SOService", targetNamespace = "http://koreaaero.com/ST", wsdlLocation = "file:/C:/Source/project/episode/src/main/resources/ws/ST00150_SOService_ICAM.wsdl")
@WebServiceClient(name = "ST00150_SOService", targetNamespace = "http://koreaaero.com/ST", wsdlLocation = "/usr/share/tomcat9/webapps/episode/WEB-INF/classes/ws/ST00150_SOService_ICAM.wsdl")
public class ST00150SOService
    extends Service
{

    private final static URL ST00150SOSERVICE_WSDL_LOCATION;
    private final static WebServiceException ST00150SOSERVICE_EXCEPTION;
    private final static QName ST00150SOSERVICE_QNAME = new QName("http://koreaaero.com/ST", "ST00150_SOService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/usr/share/tomcat9/webapps/episode/WEB-INF/classes/ws/ST00150_SOService_ICAM.wsdl");
            // url = new URL("file:/C:/Source/project/wsclient/ST00150_SOService_ICAM.wsdl");
            // url = new URL("file:/C:/Source/project/episode/src/main/resources/ws/ST00150_SOService_ICAM.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ST00150SOSERVICE_WSDL_LOCATION = url;
        ST00150SOSERVICE_EXCEPTION = e;
    }

    public ST00150SOService() {
        super(__getWsdlLocation(), ST00150SOSERVICE_QNAME);
    }

    public ST00150SOService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ST00150SOSERVICE_QNAME, features);
    }

    public ST00150SOService(URL wsdlLocation) {
        super(wsdlLocation, ST00150SOSERVICE_QNAME);
    }

    public ST00150SOService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ST00150SOSERVICE_QNAME, features);
    }

    public ST00150SOService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ST00150SOService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ST00150SO
     */
    @WebEndpoint(name = "HTTP_Port")
    public ST00150SO getHTTPPort() {
        return super.getPort(new QName("http://koreaaero.com/ST", "HTTP_Port"), ST00150SO.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ST00150SO
     */
    @WebEndpoint(name = "HTTP_Port")
    public ST00150SO getHTTPPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://koreaaero.com/ST", "HTTP_Port"), ST00150SO.class, features);
    }

    /**
     * 
     * @return
     *     returns ST00150SO
     */
    @WebEndpoint(name = "HTTPS_Port")
    public ST00150SO getHTTPSPort() {
        return super.getPort(new QName("http://koreaaero.com/ST", "HTTPS_Port"), ST00150SO.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ST00150SO
     */
    @WebEndpoint(name = "HTTPS_Port")
    public ST00150SO getHTTPSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://koreaaero.com/ST", "HTTPS_Port"), ST00150SO.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ST00150SOSERVICE_EXCEPTION!= null) {
            throw ST00150SOSERVICE_EXCEPTION;
        }
        return ST00150SOSERVICE_WSDL_LOCATION;
    }

}