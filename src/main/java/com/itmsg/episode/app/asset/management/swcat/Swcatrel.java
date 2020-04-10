/**
* 관련 카탈로그 엔터티 클래스
* @author   최영훈
* @since    2019.10.14
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.14   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.swcat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Swcatrel.TABLE_NAME)
public class Swcatrel implements Serializable {

    public static final String TABLE_NAME="SWCATREL";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Transient
    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
	
	@Column(name = "SWCAT_ID1", length = 30 )
	private String swcatId1;

	@Column(name = "SWCAT_ID2", length = 30 )
	private String swcatId2;
	 
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
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSwcatId1() {
		return swcatId1;
	}

	public void setSwcatId1(String swcatId1) {
		this.swcatId1 = swcatId1;
	}

	public String getSwcatId2() {
		return swcatId2;
	}

	public void setSwcatId2(String swcatId2) {
		this.swcatId2 = swcatId2;
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