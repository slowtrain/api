package com.itmsg.episode.app.system.scheduler;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "SCHEDULERHISTORY")
public class SchedulerHistory implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULERHISTORY_SEQ")
    @SequenceGenerator(name = "SCHEDULERHISTORY_SEQ", sequenceName = "SCHEDULERHISTORY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "SCHEDULER_ID")
	private String schedulerId;

	@Column(name = "IPADDRESS")
	private String ipAddress;

	@Column(name = "START_DT")
	private Date startDt;
	
	@Column(name = "END_DT")
    private Date endDt;

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;
	
	@Column(name = "CREATE_ID")
    private String createId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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

	

	

}
