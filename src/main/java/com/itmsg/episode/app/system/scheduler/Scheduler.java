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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "SCHEDULER")
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULER_SEQ")
    @SequenceGenerator(name = "SCHEDULER_SEQ", sequenceName = "SCHEDULER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "SCHEDULER_ID", unique = true)
	private String schedulerId;

    @Column(name = "DESCRIPTION")
    @NotNull
	private String description;

	@Column(name = "EXPRESSION")
    @NotNull
	private String expression;

	@Column(name = "PARAMETER")
	private String parameter;

	@Column(name = "HISTORYQUANTITY")
	private Integer historyQuantity;
	
    @Column(name = "STATUS")
    private String status;

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
	private Boolean isAlive;

	@Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Integer getHistoryQuantity() {
		return historyQuantity;
	}

	public void setHistoryQuantity(Integer historyQuantity) {
		this.historyQuantity = historyQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public String getCreateNm() {
		return createNm;
	}

	public void setCreateNm(String createNm) {
		this.createNm = createNm;
	}

	public String getUpdateNm() {
		return updateNm;
	}

	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}

	
}
