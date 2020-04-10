/**
* Rparespraw 엔터티 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.e2e;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Rparespraw.TABLE_NAME)
public class Rparespraw implements Serializable {


	public static final String TABLE_NAME="RPARESPRAW";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "CREATE_DT")
	@CreationTimestamp
	private Date createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	@UpdateTimestamp
	private Date updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "CI_ID")
	private String ciId;

	@Column(name = "ROBOT_DT")
	private Date robotDt;

	@Column(name = "ROBOTNAME")
	private String robotname;

	@Column(name = "ROBOTIP")
	private String robotip;

	@Column(name = "LOCATION_ID")
	private String locationId;	

	@Column(name = "ROBOTFLOOR")
	private String robotfloor;

	@Column(name = "ROBOTRESULT")
	private String robotresult;

	@Column(name = "LINEBIZ_ID")
	private String linebizId;

	@Column(name = "LINEBIZ")
	private String linebiz;

	@Column(name = "EXETOOLS")
	private String exetools;

	@Column(name = "LINEBIZSTEP")
	private String linebizstep;

	@Column(name = "LINEBIZRESULT")
	private String linebizresult;

	@Column(name = "LINEBIZSTART_DT")
	private Date linebizstartDt;

	@Column(name = "LINEBIZEND_DT")
	private Date linebizendDt;

	@Column(name = "LINEBIZRUNSEC")
	private int linebizrunsec;

	@Transient
	@Column(name = "NAMEKOR")   
    private String namekor;
	
	@Transient
	@Column(name = "CINAME")    
    private String ciname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public Date getRobotDt() {
		return robotDt;
	}

	public void setRobotDt(Date robotDt) {
		this.robotDt = robotDt;
	}

	public String getRobotname() {
		return robotname;
	}

	public void setRobotname(String robotname) {
		this.robotname = robotname;
	}

	public String getRobotip() {
		return robotip;
	}

	public void setRobotip(String robotip) {
		this.robotip = robotip;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getRobotfloor() {
		return robotfloor;
	}

	public void setRobotfloor(String robotfloor) {
		this.robotfloor = robotfloor;
	}

	public String getRobotresult() {
		return robotresult;
	}

	public void setRobotresult(String robotresult) {
		this.robotresult = robotresult;
	}

	public String getLinebizId() {
		return linebizId;
	}

	public void setLinebizId(String linebizId) {
		this.linebizId = linebizId;
	}

	public String getLinebiz() {
		return linebiz;
	}

	public void setLinebiz(String linebiz) {
		this.linebiz = linebiz;
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

	public String getLinebizresult() {
		return linebizresult;
	}

	public void setLinebizresult(String linebizresult) {
		this.linebizresult = linebizresult;
	}

	public Date getLinebizstartDt() {
		return linebizstartDt;
	}

	public void setLinebizstartDt(Date linebizstartDt) {
		this.linebizstartDt = linebizstartDt;
	}

	public Date getLinebizendDt() {
		return linebizendDt;
	}

	public void setLinebizendDt(Date linebizendDt) {
		this.linebizendDt = linebizendDt;
	}

	public int getLinebizrunsec() {
		return linebizrunsec;
	}

	public void setLinebizrunsec(int linebizrunsec) {
		this.linebizrunsec = linebizrunsec;
	}

	public String getNamekor() {
		return namekor;
	}

	public void setNamekor(String namekor) {
		this.namekor = namekor;
	}

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
	}



}