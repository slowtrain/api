/**
* 변환 Converter 엔터티 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Converter.TABLE_NAME)
public class Converter implements Serializable {

    public static final String TABLE_NAME="CONVERTER";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "CONVERTER_ID")
    private String converterId;
    
	@Column(name = "ORIGINALVALUE")
	private String originalvalue;
	
	@Column(name = "CONVERTEDVALUE")
    private String convertedvalue;
  
    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    private Date updateDt;

    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Transient
	private Boolean chk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConverterId() {
        return converterId;
    }

    public void setConverterId(String converterId) {
        this.converterId = converterId;
    }

    public String getOriginalvalue() {
        return originalvalue;
    }

    public void setOriginalvalue(String originalvalue) {
        this.originalvalue = originalvalue;
    }

    public String getConvertedvalue() {
        return convertedvalue;
    }

    public void setConvertedvalue(String convertedvalue) {
        this.convertedvalue = convertedvalue;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Boolean getChk() {
        return chk;
    }

    public void setChk(Boolean chk) {
        this.chk = chk;
    }
}