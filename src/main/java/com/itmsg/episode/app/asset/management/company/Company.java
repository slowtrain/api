/**
* 업체 엔터티 클래스
* @author   최영훈
* @since    2019.10.28
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.28   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.company;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Company.TABLE_NAME)
public class Company implements Serializable {

    public static final String TABLE_NAME="COMPANY";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "ORG_ID")
    private String orgId;
		
	@Column(name = "COMPANY_ID")
	private String companyId;
	
	@Column(name = "COMPANYNAME")
	private String companyname;

	@Column(name = "COMPANYDESCRIPTION")
	private String companydescription;

	@Column(name = "COMPANYTYPE")
	private String companytype;

	@Column(name = "COMPANYNUM")
	private String companynum;

	@Column(name = "COMPANYTEL")
	private String companytel;

	@Column(name = "OWNERNAME")
	private String ownername;

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
	@Column(name = "COMPANYTYPENM")
	private String companytypenm;

	@Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanydescription() {
		return companydescription;
	}

	public void setCompanydescription(String companydescription) {
		this.companydescription = companydescription;
	}

	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public String getCompanynum() {
		return companynum;
	}

	public void setCompanynum(String companynum) {
		this.companynum = companynum;
	}

	public String getCompanytel() {
		return companytel;
	}

	public void setCompanytel(String companytel) {
		this.companytel = companytel;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
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

	public String getCompanytypenm() {
		return companytypenm;
	}

	public void setCompanytypenm(String companytypenm) {
		this.companytypenm = companytypenm;
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