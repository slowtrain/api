/**
* 관련 카탈로그 엔터티 클래스
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
@Table(name = SwLiccat.TABLE_NAME)
public class SwLiccat implements Serializable {

    public static final String TABLE_NAME="SWLICCAT";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
		
	@Column(name = "ORG_ID", length = 30 )
	private String orgId;
		
	@Column(name = "SWLICENSE_ID", length = 30 )
	private String swlicenseId;
	
	@Column(name = "SWCAT_ID", length = 30 )
	private String swcatId;	

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
	
	public String getSwlicenseId() {
		return swlicenseId;
	}

	public void setSwlicenseId(String swlicenseId) {
		this.swlicenseId = swlicenseId;
	}

	public String getSwcatId() {
		return swcatId;
	}

	public void setSwcatId(String swcatId) {
		this.swcatId = swcatId;
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
}