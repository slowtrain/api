package com.itmsg.episode.app.asset.resource.disci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = DisCIMapToCI.TABLE_NAME)
public class DisCIMapToCI implements Serializable {

    public static final String TABLE_NAME="DISCIMAPTOCI";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "DATASRC")
	private String dataSrc;

	@Column(name = "DISCITYPE")
	private String disCIType;

	@Column(name = "DISCICLASS")
	private String disCIClass;

	@Column(name = "ISVIRTUAL" )
	private Boolean isVirtual;

	@Column(name = "CLASSSTRUCTURE_ID")
	private String classstructureId;

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
	private String classstructureNm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public String getDisCIType() {
		return disCIType;
	}

	public void setDisCIType(String disCIType) {
		this.disCIType = disCIType;
	}

	public String getDisCIClass() {
		return disCIClass;
	}

	public void setDisCIClass(String disCIClass) {
		this.disCIClass = disCIClass;
	}

	public Boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getClassstructureId() {
		return classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
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

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}
}