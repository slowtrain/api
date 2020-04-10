
package com.koreaaero.st;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.koreaaero.st package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MTST00150SEND_QNAME = new QName("http://koreaaero.com/ST", "MT_ST00150_SEND");
    private final static QName _MTST00150SENDResponse_QNAME = new QName("http://koreaaero.com/ST", "MT_ST00150_SEND_response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.koreaaero.st
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTST00150SEND }
     * 
     */
    public DTST00150SEND createDTST00150SEND() {
        return new DTST00150SEND();
    }

    /**
     * Create an instance of {@link DTST00150SENDResponse }
     * 
     */
    public DTST00150SENDResponse createDTST00150SENDResponse() {
        return new DTST00150SENDResponse();
    }

    /**
     * Create an instance of {@link DTST00150SEND.IFAMLHS }
     * 
     */
    public DTST00150SEND.IFAMLHS createDTST00150SENDIFAMLHS() {
        return new DTST00150SEND.IFAMLHS();
    }

    /**
     * Create an instance of {@link DTST00150SENDResponse.ITRETURN }
     * 
     */
    public DTST00150SENDResponse.ITRETURN createDTST00150SENDResponseITRETURN() {
        return new DTST00150SENDResponse.ITRETURN();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTST00150SEND }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://koreaaero.com/ST", name = "MT_ST00150_SEND")
    public JAXBElement<DTST00150SEND> createMTST00150SEND(DTST00150SEND value) {
        return new JAXBElement<DTST00150SEND>(_MTST00150SEND_QNAME, DTST00150SEND.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTST00150SENDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://koreaaero.com/ST", name = "MT_ST00150_SEND_response")
    public JAXBElement<DTST00150SENDResponse> createMTST00150SENDResponse(DTST00150SENDResponse value) {
        return new JAXBElement<DTST00150SENDResponse>(_MTST00150SENDResponse_QNAME, DTST00150SENDResponse.class, null, value);
    }

}
