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
@Table(name = "WFNODE")
public class WFNode implements Serializable{
	
	public static final String TABLE_NAME="WFNODE";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "WFPROCESS_UID")
	private Long wfprocessUid;

    @Column(name = "WFNODE_ID")
	private String wfnodeId;

	@Column(name = "APP_ID")
	private String appId;

	@Column(name = "ACTION_ID")
	private String actionId;

	@Column(name = "ASSIGNMENT_ID")
	private String assignmentId;

	@Column(name = "EXPRESSION")
	private String expression;

    @Column(name = "annotations_content")
	private String annotations_content;

	@Column(name = "shape_type")
	private String shape_type;

	@Column(name = "shape_shape")
	private String shape_shape;

	@Column(name = "style_fill")
	private String style_fill;
	
	@Column(name = "OFFSETX")
	private Integer offsetX;
	
	@Column(name = "OFFSETY")
	private Integer offsetY;
	
	@Column(name = "CONSTRAINTS")
    private Integer constraints;

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

	public String getWfnodeId() {
		return wfnodeId;
	}

	public void setWfnodeId(String wfnodeId) {
		this.wfnodeId = wfnodeId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getAnnotations_content() {
		return annotations_content;
	}

	public void setAnnotations_content(String annotations_content) {
		this.annotations_content = annotations_content;
	}

	public String getShape_type() {
		return shape_type;
	}

	public void setShape_type(String shape_type) {
		this.shape_type = shape_type;
	}

	public String getShape_shape() {
		return shape_shape;
	}

	public void setShape_shape(String shape_shape) {
		this.shape_shape = shape_shape;
	}

	public String getStyle_fill() {
		return style_fill;
	}

	public void setStyle_fill(String style_fill) {
		this.style_fill = style_fill;
	}

	public Integer getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	public Integer getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	public Integer getConstraints() {
		return constraints;
	}

	public void setConstraints(Integer constraints) {
		this.constraints = constraints;
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

	

	
		
}
