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
@Table(name = "SCHEDULERACTION")
public class SchedulerAction implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULERACTION_SEQ")
    @SequenceGenerator(name = "SCHEDULERACTION_SEQ", sequenceName = "SCHEDULERACTION_SEQ", allocationSize = 1)
    private Long id;

	
    @Column(name = "SCHEDULER_ID")
	private String schedulerId;

	@Column(name = "ACTION")
	private String action;

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
