package com.itmsg.episode.app.system.action;

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
@Table(name = Action.TABLE_NAME)
public class Action implements Serializable{
	
	public static final String TABLE_NAME="ACTION";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "ACTION_ID")
	private String actionId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "OBJECT_ID")
	private String objectId;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CLASSNAME")
	private String className;
	
	@Column(name = "JYTHON")
    private String jython;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getJython() {
		return jython;
	}

	public void setJython(String jython) {
		this.jython = jython;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

		

	
	
}
