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
@Table(name = "WFConnector")
public class WFConnector implements Serializable{
	
	public static final String TABLE_NAME="WFCONNECTOR";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "WFPROCESS_UID")
	private Long wfprocessUid;

	@Column(name = "WFCONNECTOR_ID")
	private String wfconnectorId;

	@Column(name = "ACTION_ID")
	private String actionId;

	@Column(name = "SOURCEID")
	private String sourceID;

	@Column(name = "TARGETID")
	private String targetID;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "annotations_content")
	private String annotations_content;

	@Column(name = "style_strokecolor")
	private String style_strokeColor;
	
	@Column(name = "sourcepoint_x")
	private Double sourcePoint_x;
	
	@Column(name = "sourcepoint_y")
	private Double sourcePoint_y;

	@Column(name = "targetpoint_x")
	private Double targetPoint_x;
	
	@Column(name = "targetpoint_y")
	private Double targetPoint_y;

	@Column(name = "CREATE_ID")
    private String createId;
 
    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;
 
    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    private Date updateDt;
 
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

	public Long getWfprocessUid() {
		return wfprocessUid;
	}

	public void setWfprocessUid(Long wfprocessUid) {
		this.wfprocessUid = wfprocessUid;
	}

	public String getWfconnectorId() {
		return wfconnectorId;
	}

	public void setWfconnectorId(String wfconnectorId) {
		this.wfconnectorId = wfconnectorId;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getTargetID() {
		return targetID;
	}

	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle_strokeColor() {
		return style_strokeColor;
	}

	public void setStyle_strokeColor(String style_strokeColor) {
		this.style_strokeColor = style_strokeColor;
	}

	public Double getSourcePoint_x() {
		return sourcePoint_x;
	}

	public void setSourcePoint_x(Double sourcePoint_x) {
		this.sourcePoint_x = sourcePoint_x;
	}

	public Double getSourcePoint_y() {
		return sourcePoint_y;
	}

	public void setSourcePoint_y(Double sourcePoint_y) {
		this.sourcePoint_y = sourcePoint_y;
	}

	public Double getTargetPoint_x() {
		return targetPoint_x;
	}

	public void setTargetPoint_x(Double targetPoint_x) {
		this.targetPoint_x = targetPoint_x;
	}

	public Double getTargetPoint_y() {
		return targetPoint_y;
	}

	public void setTargetPoint_y(Double targetPoint_y) {
		this.targetPoint_y = targetPoint_y;
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

	public String getAnnotations_content() {
		return annotations_content;
	}

	public void setAnnotations_content(String annotations_content) {
		this.annotations_content = annotations_content;
	}

	

	
	
}
