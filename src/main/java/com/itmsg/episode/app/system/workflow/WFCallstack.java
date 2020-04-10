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
@Table(name = WFCallstack.TABLE_NAME)
public class WFCallstack implements Serializable{
	
	public static final String TABLE_NAME="WFCALLSTACK";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "WFINSTANCE_UID")
	private Long wfinstanceUid;

	@Column(name = "WFCONNECTOR_UID")
	private Long wfconnectorUid;

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

	public Long getWfinstanceUid() {
		return wfinstanceUid;
	}

	public void setWfinstanceUid(Long wfinstanceUid) {
		this.wfinstanceUid = wfinstanceUid;
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

	public Long getWfconnectorUid() {
		return wfconnectorUid;
	}

	public void setWfconnectorUid(Long wfconnectorUid) {
		this.wfconnectorUid = wfconnectorUid;
	}


		

}
