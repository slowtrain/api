/**
* ITSM CI 엔터티 클래스
* @author   최영훈
* @since    2019.10.31
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.31   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = ItsmCI.TABLE_NAME)
public class ItsmCI implements Serializable {
     
    public static final String TABLE_NAME="IF_ITSM_WTCI";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
	    
    @Column(name = "CI_ID")
	private String ciId;

	@Column(name = "CI_TYPE_ID")
	private String ciTypeId;

	@Column(name = "KAI_CIID")
	private String kaiCiid;

	@Column(name = "CI_NAME")
	private String ciName;
	
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

	@Transient
	@Column(name = "CI_TYPE_NM")
	private String ciTypeNm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getCiTypeId() {
		return ciTypeId;
	}

	public void setCiTypeId(String ciTypeId) {
		this.ciTypeId = ciTypeId;
	}

	public String getKaiCiid() {
		return kaiCiid;
	}

	public void setKaiCiid(String kaiCiid) {
		this.kaiCiid = kaiCiid;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
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

	public String getCiTypeNm() {
		return ciTypeNm;
	}

	public void setCiTypeNm(String ciTypeNm) {
		this.ciTypeNm = ciTypeNm;
	}
}