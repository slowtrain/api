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
@Table(name = "WFPROCESS")
public class WFProcess implements Serializable{
	
	public static final String TABLE_NAME="WFPROCESS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "WFPROCESS_ID")
	private String wfprocessId;
	
	@Column(name = "REVISION")
    private Integer revision;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
	private String status;

	@Column(name = "OBJECT_ID")
	private String objectId;
	
	@Column(name = "ENABLED")
    private Boolean enabled;

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
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

	@Transient
	private List<WFNode> nodes;

	@Transient
	private List<WFConnector> connectors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWfprocessId() {
		return wfprocessId;
	}

	public void setWfprocessId(String wfprocessId) {
		this.wfprocessId = wfprocessId;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

	public List<WFNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<WFNode> nodes) {
		this.nodes = nodes;
	}

	public List<WFConnector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<WFConnector> connectors) {
		this.connectors = connectors;
	}

	

	
		
}
