/**
* EVENTDUR 엔터티 클래스
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
import java.util.Date;

@Entity
@Table(name = Eventdur.TABLE_NAME)
public class Eventdur implements Serializable {

    public static final String TABLE_NAME="EVENTDUR";
	
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "CI_ID")	
	private String ciId;

	@Column(name = "DATASRCKEYINT")
	private Long datasrckeyint;

	@Column(name = "DATASRCKEYCHAR")
	private String datasrckeychar;

	@Column(name = "DATASRC")
	private String datasrc;

	@Column(name = "ORIGIN_START_DT")
	private Date originStartDt;

	@Column(name = "ORIGIN_END_DT")
	private Date originEndDt;

	@Column(name = "ORIGIN_START_SEQ")
	private Long originStartSeq;

	@Column(name = "ORIGIN_END_SEQ")
	private Long originEndSeq;

	@Column(name = "DURATION_TIME")
	private Long durationTime;

	@Column(name = "CREATEDT")
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
	@Column(name = "DOWNTIME")
	private String downtime;

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

	public Long getDatasrckeyint() {
		return datasrckeyint;
	}

	public void setDatasrckeyint(Long datasrckeyint) {
		this.datasrckeyint = datasrckeyint;
	}

	public String getDatasrckeychar() {
		return datasrckeychar;
	}

	public void setDatasrckeychar(String datasrckeychar) {
		this.datasrckeychar = datasrckeychar;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public Date getOriginStartDt() {
		return originStartDt;
	}

	public void setOriginStartDt(Date originStartDt) {
		this.originStartDt = originStartDt;
	}

	public Date getOriginEndDt() {
		return originEndDt;
	}

	public void setOriginEndDt(Date originEndDt) {
		this.originEndDt = originEndDt;
	}

	public Long getOriginStartSeq() {
		return originStartSeq;
	}

	public void setOriginStartSeq(Long originStartSeq) {
		this.originStartSeq = originStartSeq;
	}

	public Long getOriginEndSeq() {
		return originEndSeq;
	}

	public void setOriginEndSeq(Long originEndSeq) {
		this.originEndSeq = originEndSeq;
	}

	public Long getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
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

	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}
}