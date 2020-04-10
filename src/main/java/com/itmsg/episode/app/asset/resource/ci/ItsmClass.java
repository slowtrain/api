/**
* ITSM 분류 엔터티 클래스
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
@Table(name = ItsmClass.TABLE_NAME)
public class ItsmClass implements Serializable {
     
    public static final String TABLE_NAME="IF_ITSM_WTCLS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
	
	@Column(name = "CI_TYPE_ID")
	private String ciTypeId;

	@Column(name = "CI_TYPE_NAME")
	private String ciTypeName;

	@Column(name = "PARENT_CI_TYPE_ID")
	private String parentCiTypeId;

	@Column(name = "DEPTH_FULLNAME")
	private String depthFullname;
	
	@Column(name = "DEPTH_FULLID")
	private String depthFullid;
	
	@Column(name = "ROOTCLASSID")
	private String rootclassid;
	
	@Column(name = "USESTATUS")
    private String usestatus;

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

	public String getCiTypeId() {
		return ciTypeId;
	}

	public void setCiTypeId(String ciTypeId) {
		this.ciTypeId = ciTypeId;
	}

	public String getCiTypeName() {
		return ciTypeName;
	}

	public void setCiTypeName(String ciTypeName) {
		this.ciTypeName = ciTypeName;
	}

	public String getParentCiTypeId() {
		return parentCiTypeId;
	}

	public void setParentCiTypeId(String parentCiTypeId) {
		this.parentCiTypeId = parentCiTypeId;
	}

	public String getDepthFullname() {
		return depthFullname;
	}

	public void setDepthFullname(String depthFullname) {
		this.depthFullname = depthFullname;
	}

	public String getDepthFullid() {
		return depthFullid;
	}

	public void setDepthFullid(String depthFullid) {
		this.depthFullid = depthFullid;
	}

	public String getRootclassid() {
		return rootclassid;
	}

	public void setRootclassid(String rootclassid) {
		this.rootclassid = rootclassid;
	}

	public String getUsestatus() {
		return usestatus;
	}

	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
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