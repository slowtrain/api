/**
* 분류 관계 정의 맵 엔터티 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = CIrelationmap.TABLE_NAME)
public class CIrelationmap implements Serializable {
	
    public static final String TABLE_NAME="CIRELATIONMAP";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID")
    private String orgId;
        
    @Column(name = "SOURCECLASS")
	private String sourceclass;
	
	@Column(name = "TARGETCLASS")
	private String targetclass;	
	
	@Column(name = "RELATIONNAME")
    private String relationname;
    
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

	@Transient
	@Column(name = "SOURCECLASS_NM")
	private String sourceclassNm;

	@Transient
	@Column(name = "TARGETCLASS_NM")
	private String targetclassNm;	

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

	public String getSourceclass() {
		return this.sourceclass;
	}

	public void setSourceclass(String sourceclass) {
		this.sourceclass = sourceclass;
	}
	
	public String getTargetclass	() {
		return this.targetclass	;
	}

	public void setTargetclass	(String targetclass	) {
		this.targetclass = targetclass	;
	}
	
	public String getRelationname() {
		return this.relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
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

	public String getSourceclassNm() {
		return sourceclassNm;
	}

	public void setSourceclassNm(String sourceclassNm) {
		this.sourceclassNm = sourceclassNm;
	}

	public String getTargetclassNm() {
		return targetclassNm;
	}

	public void setTargetclassNm(String targetclassNm) {
		this.targetclassNm = targetclassNm;
	}
}