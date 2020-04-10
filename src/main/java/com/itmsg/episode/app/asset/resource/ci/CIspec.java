/**
* IT자원 SPEC 엔터티 클래스
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
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = CIspec.TABLE_NAME)
public class CIspec implements Serializable {
	
    public static final String TABLE_NAME="CISPEC";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
        
    @Column(name = "CI_ID", length = 30 )
	private String ciId;
	
	@Column(name = "CLASSSTRUCTURE_ID", length = 30 )
	private String classstructureId;
	
	@Column(name = "CLASSSPEC_ID", length = 30 )
    private String classspecId;

    @Column(name = "TYPE", length = 10 )
    private String type;
    
    @Column(name = "ALNVALUE", length = 100 )
	private String alnvalue;

	@Column(name = "NUMVALUE" )
    private BigDecimal numvalue;

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
	private String description;

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

	public String getCiId() {
		return this.ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getClassstructureId() {
		return this.classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getClassspecId() {
		return this.classspecId;
	}

	public void setClassspecId(String classspecId) {
		this.classspecId = classspecId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlnvalue() {
		return this.alnvalue;
	}

	public void setAlnvalue(String alnvalue) {
		this.alnvalue = alnvalue;
	}

	public BigDecimal getNumvalue() {
		return this.numvalue;
	}

	public void setNumvalue(BigDecimal numvalue) {
		this.numvalue = numvalue;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}