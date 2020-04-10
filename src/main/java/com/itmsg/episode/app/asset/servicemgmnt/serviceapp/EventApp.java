/**
* EVENTAPP 엔터티 클래스
* @author   이정호
* @since    2019.11.25
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.25   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.servicemgmnt.serviceapp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = EventApp.TABLE_NAME)
public class EventApp implements Serializable {
	
    public static final String TABLE_NAME="EVENTAPP";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
        
    @Column(name = "CI_ID")
	private String ciId;
	
	@Column(name = "COLL_DT")
	private Date collDt;	
	
	@Column(name = "TOTAL_TIME")
    private int totalTime;
    
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
	@Column(name = "CINAME")
	private String ciname;

	@Transient
	@Column(name = "CLASSSTRUCTURE_ID")
	private String classstructureId;
	
	@Transient
	@Column(name = "CLASSSTRUCTURE_NM")
	private String classstructureNm;

	@Transient
	@Column(name = "LOSS_COST")
	private String lossCost;

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

	public Date getCollDt() {
		return collDt;
	}

	public void setCollDt(Date collDt) {
		this.collDt = collDt;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
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

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
	}

	public String getClassstructureId() {
		return classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}

	public String getLossCost() {
		return lossCost;
	}

	public void setLossCost(String lossCost) {
		this.lossCost = lossCost;
	}

}