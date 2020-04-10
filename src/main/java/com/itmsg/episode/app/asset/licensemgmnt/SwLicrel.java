/**
* 관련 라이선스 엔터티 클래스
* @author   최영훈
* @since    2019.09.16
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.16   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = SwLicrel.TABLE_NAME)
public class SwLicrel implements Serializable {

    public static final String TABLE_NAME="SWLICREL";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "ORG_ID", length = 30 )
	private String orgId;
	
	@Column(name = "SWLICENSE_ID1", length = 30 )
	private String swlicenseId1;
	
	@Column(name = "SWLICENSE_ID2", length = 30 )
	private String swlicenseId2;	

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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getSwlicenseId1() {
		return swlicenseId1;
	}

	public void setSwlicenseId1(String swlicenseId1) {
		this.swlicenseId1 = swlicenseId1;
	}

	public String getSwlicenseId2() {
		return swlicenseId2;
	}

	public void setSwlicenseId2(String swlicenseId2) {
		this.swlicenseId2 = swlicenseId2;
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