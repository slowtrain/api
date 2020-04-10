/**
* 위치 엔터티 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Location.TABLE_NAME)
public class Location implements Serializable {

    public static final String TABLE_NAME="LOCATION";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
    
    @Column(name = "PARENT", length = 30 )
    private String parent;
    
    @Column(name = "LOCATION_ID", length = 30 )
    private String locationId;

    @Column(name = "STATUS", length = 10 )
    private String status;

    @Column(name = "TYPE", length = 10 )
    private String type;
    
    @Column(name = "DESCRIPTION", length = 50 )
    private String description;

	@Column(name = "NAMEKOR", length = 50 )
	private String namekor;

	@Column(name = "NAMEENG", length = 50 )
    private String nameeng;
	

    @Column(name = "HIERARCHYPATH", length = 100 )
    private String hierarchypath;

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    private Date updateDt;

    @Column(name = "UPDATE_ID")
	private String updateId;

	@Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;
	
	@Transient
	@Column(name = "CHK")
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

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getLocationId() {
		return this.locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHierarchypath() {
		return this.hierarchypath;
	}

	public void setHierarchypath(String hierarchypath) {
		this.hierarchypath = hierarchypath;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
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

	public String getNamekor() {
		return namekor;
	}

	public void setNamekor(String namekor) {
		this.namekor = namekor;
	}

	public String getNameeng() {
		return nameeng;
	}

	public void setNameeng(String nameeng) {
		this.nameeng = nameeng;
	}

	public String getCreateNm() {
		return createNm;
	}

	public void setCreateNm(String createNm) {
		this.createNm = createNm;
	}

	public String getUpdateNm() {
		return updateNm;
	}

	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}
}