
package com.itmsg.episode.integration.sappo.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "syncSappo", namespace = "http://sappo.integration.episode.itmsg.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "syncSappo", namespace = "http://sappo.integration.episode.itmsg.com/")
public class SyncSappo {

    @XmlElement(name = "datas", namespace = "")
    private List<com.itmsg.episode.integration.Integration> datas;

    /**
     * 
     * @return
     *     returns List<Integration>
     */
    public List<com.itmsg.episode.integration.Integration> getDatas() {
        return this.datas;
    }

    /**
     * 
     * @param datas
     *     the value for the datas property
     */
    public void setDatas(List<com.itmsg.episode.integration.Integration> datas) {
        this.datas = datas;
    }

}
