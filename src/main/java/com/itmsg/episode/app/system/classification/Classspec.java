/**
* 분류 스펙 엔터티 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Classspec.TABLE_NAME)
public class Classspec implements Serializable {

    public static final String TABLE_NAME="CLASSSPEC";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
        
    @Column(name = "CLASSSTRUCTURE_ID", length = 30 )
    private String classstructureId;

    @Column(name = "CLASSSPEC_ID", length = 30 )
    private String classspecId;
    
    @Column(name = "DESCRIPTION", length = 50 )
    private String description;
    
    @Column(name = "TYPE", length = 10 )
    private String type;
    
    @Column(name = "MEASUREUNIT", length = 10 )
    private String measureunit;

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
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getClassstructureId() {
		return this.classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

    public String getClassspecId() {
		return this.classspecId;
	}

	public void setClassspecId(String classspecId) {
		this.classspecId = classspecId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMeasureunit() {
		return this.measureunit;
	}

	public void setMeasureunit(String measureunit) {
		this.measureunit = measureunit;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	public String getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Boolean getChk() {
		return this.chk;
	}

	public void setChk(Boolean chk) {
		this.chk = chk;
	}
}