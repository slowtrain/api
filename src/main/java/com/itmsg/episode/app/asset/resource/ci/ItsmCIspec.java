/**
* ITSM CI SPEC 엔터티 클래스
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
@Table(name = ItsmCIspec.TABLE_NAME)
public class ItsmCIspec implements Serializable {
     
    public static final String TABLE_NAME="IF_ITSM_WTCISPEC";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
	    
    @Column(name = "CI_ID")
	private String ciId;

	@Column(name = "CI_ATTR_ID")
	private String ciAttrId;

	@Column(name = "CI_ATTR_NAME")
	private String ciAttrName;
	
	@Column(name = "CI_ATTR_VALUE_LAST")
	private String ciAttrValueLast;
	
	@Column(name = "CI_ATTR_VALUE")
	private String ciAttrValue;
	
	@Column(name = "CI_BASICINFO_SUBNAME")
	private String ciBasicinfoSubname;
	
	@Column(name = "CI_BASICINFO_ID")
	private String ciBasicinfoId;

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

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getCiAttrId() {
		return ciAttrId;
	}

	public void setCiAttrId(String ciAttrId) {
		this.ciAttrId = ciAttrId;
	}

	public String getCiAttrName() {
		return ciAttrName;
	}

	public void setCiAttrName(String ciAttrName) {
		this.ciAttrName = ciAttrName;
	}

	public String getCiAttrValueLast() {
		return ciAttrValueLast;
	}

	public void setCiAttrValueLast(String ciAttrValueLast) {
		this.ciAttrValueLast = ciAttrValueLast;
	}

	public String getCiAttrValue() {
		return ciAttrValue;
	}

	public void setCiAttrValue(String ciAttrValue) {
		this.ciAttrValue = ciAttrValue;
	}

	public String getCiBasicinfoSubname() {
		return ciBasicinfoSubname;
	}

	public void setCiBasicinfoSubname(String ciBasicinfoSubname) {
		this.ciBasicinfoSubname = ciBasicinfoSubname;
	}

	public String getCiBasicinfoId() {
		return ciBasicinfoId;
	}

	public void setCiBasicinfoId(String ciBasicinfoId) {
		this.ciBasicinfoId = ciBasicinfoId;
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