/**
* RPARESP 맵 엔터티 클래스
* @author   최영훈
* @since    2019.10.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Rparespmap.TABLE_NAME)
public class Rparespmap implements Serializable {
	
    public static final String TABLE_NAME="RPARESPMAP";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
        
    @Column(name = "CI_ID")
	private String ciId;
	
	@Column(name = "EXETOOLS")
	private String exetools;	
	
	@Column(name = "LINEBIZSTEP")
    private String linebizstep;
    
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

	public String getExetools() {
		return exetools;
	}

	public void setExetools(String exetools) {
		this.exetools = exetools;
	}

	public String getLinebizstep() {
		return linebizstep;
	}

	public void setLinebizstep(String linebizstep) {
		this.linebizstep = linebizstep;
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