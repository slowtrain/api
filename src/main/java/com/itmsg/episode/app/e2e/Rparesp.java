/**
* Rpa 엔터티 클래스
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
@Table(name = Rparesp.TABLE_NAME)
public class Rparesp implements Serializable {


	public static final String TABLE_NAME="RPARESP";

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

	@Column(name = "LOCATION_ID")
	private String locationId;

	@Column(name = "LINEBIZ_ID")
	private String linebizId;

	@Column(name = "ROBOTRESULT")
	private String robotresult;

	@Column(name = "LINEBIZSTART_DT")
	private Date linebizstartDt;

	@Column(name = "LINEBIZEND_DT")
	private Date linebizendDt;

	@Column(name = "LINEBIZRESPTIME")
	private Long linebizresptime;

	@Transient   
    private String namekor;

	@Transient   
    private String timeSize;

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

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLinebizId() {
		return linebizId;
	}

	public void setLinebizId(String linebizId) {
		this.linebizId = linebizId;
	}

	public String getRobotresult() {
		return robotresult;
	}

	public void setRobotresult(String robotresult) {
		this.robotresult = robotresult;
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

	public Long getLinebizresptime() {
		return linebizresptime;
	}

	public void setLinebizresptime(Long linebizresptime) {
		this.linebizresptime = linebizresptime;
	}

	public String getNamekor() {
		return namekor;
	}

	public void setNamekor(String namekor) {
		this.namekor = namekor;
	}

	public String getTimeSize() {
		return timeSize;
	}

	public void setTimeSize(String timeSize) {
		this.timeSize = timeSize;
	}




}