/**
* 관련CI 엔터티 클래스
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
@Table(name = CIrelation.TABLE_NAME)
public class CIrelation implements Serializable {

    public static final String TABLE_NAME="CIRELATION";
	
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
        
    @Column(name = "SOURCECI", length = 30 )
	private String sourceci;
	
	@Column(name = "TARGETCI", length = 30 )
	private String targetci;	
	
	@Column(name = "RELATIONNAME", length = 20 )
    private String relationname;

    @Column(name = "STATUS", length = 10 )
    private String status;

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    private Date updateDt;

	@Column(name = "END_DT")
    private Date endDt;
	
    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "UPDATE_ID")
    private String updateId;

	@Column(name = "USERDEFINE")
	private Boolean userdefine;
	
	@Transient
	private String ciId;

	@Transient
	private Long sourceciId;

	@Transient
	private Long targetciId;

	@Transient
	private String sourceclass;

	@Transient
	private String targetclass;

	@Transient
	private String sourceciNm;

	@Transient
	private String targetciNm;

	@Transient	
	private String sourceManagerNm;

	@Transient	
	private String targetManagerNm;

	@Transient	
	private Boolean moveFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSourceci() {
		return sourceci;
	}

	public void setSourceci(String sourceci) {
		this.sourceci = sourceci;
	}

	public String getTargetci() {
		return targetci;
	}

	public void setTargetci(String targetci) {
		this.targetci = targetci;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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

	public Boolean getUserdefine() {
		return userdefine;
	}

	public void setUserdefine(Boolean userdefine) {
		this.userdefine = userdefine;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public Long getSourceciId() {
		return sourceciId;
	}

	public void setSourceciId(Long sourceciId) {
		this.sourceciId = sourceciId;
	}

	public Long getTargetciId() {
		return targetciId;
	}

	public void setTargetciId(Long targetciId) {
		this.targetciId = targetciId;
	}

	public String getSourceclass() {
		return sourceclass;
	}

	public void setSourceclass(String sourceclass) {
		this.sourceclass = sourceclass;
	}

	public String getTargetclass() {
		return targetclass;
	}

	public void setTargetclass(String targetclass) {
		this.targetclass = targetclass;
	}

	public String getSourceciNm() {
		return sourceciNm;
	}

	public void setSourceciNm(String sourceciNm) {
		this.sourceciNm = sourceciNm;
	}

	public String getTargetciNm() {
		return targetciNm;
	}

	public void setTargetciNm(String targetciNm) {
		this.targetciNm = targetciNm;
	}

	public String getSourceManagerNm() {
		return sourceManagerNm;
	}

	public void setSourceManagerNm(String sourceManagerNm) {
		this.sourceManagerNm = sourceManagerNm;
	}

	public String getTargetManagerNm() {
		return targetManagerNm;
	}

	public void setTargetManagerNm(String targetManagerNm) {
		this.targetManagerNm = targetManagerNm;
	}

	public Boolean getMoveFlag() {
		return moveFlag;
	}

	public void setMoveFlag(Boolean moveFlag) {
		this.moveFlag = moveFlag;
	}
}