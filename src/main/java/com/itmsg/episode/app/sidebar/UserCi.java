/**
* 관심장비 엔터티 클래스
* @author   이정호
* @since    2019.08.13
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.13   이정호   최초 생성
*/
package com.itmsg.episode.app.sidebar;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = UserCi.TABLE_NAME)
public class UserCi implements Serializable {

    public static final String TABLE_NAME="USERCI";
 
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ORG_ID")
	private String orgId;

	@Column(name = "CI_ID")
	private String ciId;

	@Column(name = "CREATE_DT")
    @UpdateTimestamp
	private Date createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Transient 
	private String classstructureId;

	@Transient 
	private String ciDescription;

	@Transient 
	private String idCi;

	public String getIdCi() {
		return this.idCi;
	}

	public void setIdCi(String idCi) {
		this.idCi = idCi;
	}


	public String getClassstructureId() {
		return this.classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getCiDescription() {
		return this.ciDescription;
	}

	public void setCiDescription(String ciDescription) {
		this.ciDescription = ciDescription;
	}

	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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


}