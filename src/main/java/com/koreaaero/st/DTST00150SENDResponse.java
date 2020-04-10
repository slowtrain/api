
package com.koreaaero.st;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DT_ST00150_SEND_response complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="DT_ST00150_SEND_response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IT_RETURN" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ZIFSTT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ZIFDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ZIFTM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ZIFRSTMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ZMAILURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DT_ST00150_SEND_response", propOrder = {
    "itreturn"
})
public class DTST00150SENDResponse {

    @XmlElement(name = "IT_RETURN")
    protected DTST00150SENDResponse.ITRETURN itreturn;

    /**
     * itreturn 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link DTST00150SENDResponse.ITRETURN }
     *     
     */
    public DTST00150SENDResponse.ITRETURN getITRETURN() {
        return itreturn;
    }

    /**
     * itreturn 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link DTST00150SENDResponse.ITRETURN }
     *     
     */
    public void setITRETURN(DTST00150SENDResponse.ITRETURN value) {
        this.itreturn = value;
    }


    /**
     * <p>anonymous complex type에 대한 Java 클래스입니다.
     * 
     * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ZIFSTT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ZIFDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ZIFTM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ZIFRSTMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ZMAILURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "zifstt",
        "zifdt",
        "ziftm",
        "zifrstmsg",
        "zmailurl"
    })
    public static class ITRETURN {

        @XmlElement(name = "ZIFSTT")
        protected String zifstt;
        @XmlElement(name = "ZIFDT")
        protected String zifdt;
        @XmlElement(name = "ZIFTM")
        protected String ziftm;
        @XmlElement(name = "ZIFRSTMSG")
        protected String zifrstmsg;
        @XmlElement(name = "ZMAILURL")
        protected String zmailurl;

        /**
         * zifstt 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZIFSTT() {
            return zifstt;
        }

        /**
         * zifstt 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZIFSTT(String value) {
            this.zifstt = value;
        }

        /**
         * zifdt 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZIFDT() {
            return zifdt;
        }

        /**
         * zifdt 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZIFDT(String value) {
            this.zifdt = value;
        }

        /**
         * ziftm 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZIFTM() {
            return ziftm;
        }

        /**
         * ziftm 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZIFTM(String value) {
            this.ziftm = value;
        }

        /**
         * zifrstmsg 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZIFRSTMSG() {
            return zifrstmsg;
        }

        /**
         * zifrstmsg 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZIFRSTMSG(String value) {
            this.zifrstmsg = value;
        }

        /**
         * zmailurl 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZMAILURL() {
            return zmailurl;
        }

        /**
         * zmailurl 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZMAILURL(String value) {
            this.zmailurl = value;
        }

    }

}
