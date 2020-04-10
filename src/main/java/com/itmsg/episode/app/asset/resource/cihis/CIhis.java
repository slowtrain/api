/**
* IT 자원 변경 이력 엔터티 클래스
* @author   최영훈
* @since    2019.11.18
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.18   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cihis;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = CIhis.TABLE_NAME)
public class CIhis implements Serializable {

    public static final String TABLE_NAME="CIHIS";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
        
    @Column(name = "CI_ID", length = 30 )
	private String ciId;
	
	@Column(name = "CHGTYPE", length = 30 )
    private String chgtype;

    @Column(name = "CHGITEM", length = 30 )
    private String chgitem;
    
    @Column(name = "FROMVALUE", length = 30 )
	private String fromvalue;

	@Column(name = "TOVALUE", length = 30 )
	private String tovalue;
	
	@Column(name = "CHGDETAIL", length = 100 )
	private String chgdetail;
	
	@Column(name = "CHGDESCRIPTION", length = 200 )
	private String chgdescription;

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
	@Column(name = "UPDATE_NM")
	private String updateNm;
	
	@Transient
	@Column(name = "CINAME", length = 30 )
	private String ciname;

	@Transient
    @Column(name = "CLASSSTRUCTURE_NM")
    private String classstructureNm;

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

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getChgtype() {
		return chgtype;
	}

	public void setChgtype(String chgtype) {
		this.chgtype = chgtype;
	}

	public String getChgitem() {
		return chgitem;
	}

	public void setChgitem(String chgitem) {
		this.chgitem = chgitem;
	}

	public String getFromvalue() {
		return fromvalue;
	}

	public void setFromvalue(String fromvalue) {
		this.fromvalue = fromvalue;
	}

	public String getTovalue() {
		return tovalue;
	}

	public void setTovalue(String tovalue) {
		this.tovalue = tovalue;
	}

	public String getChgdetail() {
		return chgdetail;
	}

	public void setChgdetail(String chgdetail) {
		this.chgdetail = chgdetail;
	}

	public String getChgdescription() {
		return chgdescription;
	}

	public void setChgdescription(String chgdescription) {
		this.chgdescription = chgdescription;
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

	public String getUpdateNm() {
		return updateNm;
	}

	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
	}

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}
}