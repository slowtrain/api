
package com.koreaaero.st;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DT_ST00150_SEND complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="DT_ST00150_SEND">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IFAMLHS" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MAIL_FROM_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_TO_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_CC_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_BCC_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_TIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_CONT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MAIL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ATTACH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ATTACH_FILENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ATTACH_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DMSTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DMSNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DMSPART" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DMSVERSION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DMSINDEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DT_ST00150_SEND", propOrder = {
    "ifamlhs"
})
public class DTST00150SEND {

    @XmlElement(name = "IFAMLHS")
    protected DTST00150SEND.IFAMLHS ifamlhs;

    /**
     * ifamlhs 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link DTST00150SEND.IFAMLHS }
     *     
     */
    public DTST00150SEND.IFAMLHS getIFAMLHS() {
        return ifamlhs;
    }

    /**
     * ifamlhs 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link DTST00150SEND.IFAMLHS }
     *     
     */
    public void setIFAMLHS(DTST00150SEND.IFAMLHS value) {
        this.ifamlhs = value;
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
     *         &lt;element name="MAIL_FROM_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_TO_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_CC_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_BCC_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_TIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_CONT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MAIL_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ATTACH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ATTACH_FILENAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ATTACH_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DMSTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DMSNUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DMSPART" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DMSVERSION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DMSINDEX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "mailfromaddr",
        "mailtoaddr",
        "mailccaddr",
        "mailbccaddr",
        "mailtit",
        "mailcont",
        "mailtype",
        "attach",
        "attachfilename",
        "attachtype",
        "dmstype",
        "dmsnumber",
        "dmspart",
        "dmsversion",
        "dmsindex"
    })
    public static class IFAMLHS {

        @XmlElement(name = "MAIL_FROM_ADDR")
        protected String mailfromaddr;
        @XmlElement(name = "MAIL_TO_ADDR")
        protected String mailtoaddr;
        @XmlElement(name = "MAIL_CC_ADDR")
        protected String mailccaddr;
        @XmlElement(name = "MAIL_BCC_ADDR")
        protected String mailbccaddr;
        @XmlElement(name = "MAIL_TIT")
        protected String mailtit;
        @XmlElement(name = "MAIL_CONT")
        protected String mailcont;
        @XmlElement(name = "MAIL_TYPE")
        protected String mailtype;
        @XmlElement(name = "ATTACH")
        protected String attach;
        @XmlElement(name = "ATTACH_FILENAME")
        protected String attachfilename;
        @XmlElement(name = "ATTACH_TYPE")
        protected String attachtype;
        @XmlElement(name = "DMSTYPE")
        protected String dmstype;
        @XmlElement(name = "DMSNUMBER")
        protected String dmsnumber;
        @XmlElement(name = "DMSPART")
        protected String dmspart;
        @XmlElement(name = "DMSVERSION")
        protected String dmsversion;
        @XmlElement(name = "DMSINDEX")
        protected String dmsindex;

        /**
         * mailfromaddr 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILFROMADDR() {
            return mailfromaddr;
        }

        /**
         * mailfromaddr 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILFROMADDR(String value) {
            this.mailfromaddr = value;
        }

        /**
         * mailtoaddr 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILTOADDR() {
            return mailtoaddr;
        }

        /**
         * mailtoaddr 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILTOADDR(String value) {
            this.mailtoaddr = value;
        }

        /**
         * mailccaddr 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILCCADDR() {
            return mailccaddr;
        }

        /**
         * mailccaddr 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILCCADDR(String value) {
            this.mailccaddr = value;
        }

        /**
         * mailbccaddr 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILBCCADDR() {
            return mailbccaddr;
        }

        /**
         * mailbccaddr 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILBCCADDR(String value) {
            this.mailbccaddr = value;
        }

        /**
         * mailtit 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILTIT() {
            return mailtit;
        }

        /**
         * mailtit 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILTIT(String value) {
            this.mailtit = value;
        }

        /**
         * mailcont 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILCONT() {
            return mailcont;
        }

        /**
         * mailcont 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILCONT(String value) {
            this.mailcont = value;
        }

        /**
         * mailtype 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMAILTYPE() {
            return mailtype;
        }

        /**
         * mailtype 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMAILTYPE(String value) {
            this.mailtype = value;
        }

        /**
         * attach 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getATTACH() {
            return attach;
        }

        /**
         * attach 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setATTACH(String value) {
            this.attach = value;
        }

        /**
         * attachfilename 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getATTACHFILENAME() {
            return attachfilename;
        }

        /**
         * attachfilename 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setATTACHFILENAME(String value) {
            this.attachfilename = value;
        }

        /**
         * attachtype 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getATTACHTYPE() {
            return attachtype;
        }

        /**
         * attachtype 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setATTACHTYPE(String value) {
            this.attachtype = value;
        }

        /**
         * dmstype 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDMSTYPE() {
            return dmstype;
        }

        /**
         * dmstype 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDMSTYPE(String value) {
            this.dmstype = value;
        }

        /**
         * dmsnumber 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDMSNUMBER() {
            return dmsnumber;
        }

        /**
         * dmsnumber 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDMSNUMBER(String value) {
            this.dmsnumber = value;
        }

        /**
         * dmspart 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDMSPART() {
            return dmspart;
        }

        /**
         * dmspart 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDMSPART(String value) {
            this.dmspart = value;
        }

        /**
         * dmsversion 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDMSVERSION() {
            return dmsversion;
        }

        /**
         * dmsversion 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDMSVERSION(String value) {
            this.dmsversion = value;
        }

        /**
         * dmsindex 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDMSINDEX() {
            return dmsindex;
        }

        /**
         * dmsindex 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDMSINDEX(String value) {
            this.dmsindex = value;
        }

    }

}
