package com.itmsg.episode.app.system.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = WFInstance.TABLE_NAME)
public class WFInstance implements Serializable{
	
	public static final String TABLE_NAME="WFINSTANCE";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "WFPROCESS_UID")
	private Long wfprocessUid;
	
    @Column(name = "OBJECT_UID")
	private Long objectUid;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWfprocessUid() {
		return wfprocessUid;
	}

	public void setWfprocessUid(Long wfprocessUid) {
		this.wfprocessUid = wfprocessUid;
	}

	public Long getObjectUid() {
		return objectUid;
	}

	public void setObjectUid(Long objectUid) {
		this.objectUid = objectUid;
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

	
	
}
