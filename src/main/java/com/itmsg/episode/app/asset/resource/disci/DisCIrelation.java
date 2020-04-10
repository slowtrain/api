package com.itmsg.episode.app.asset.resource.disci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = DisCIrelation.TABLE_NAME)
public class DisCIrelation implements Serializable {

    public static final String TABLE_NAME="DISCIRELATION";

    @Id
    @Column(name = "ID")
    private Long id;

	@Column(name = "SRCDISCI_ID", length = 30 )
	private String srcdisciId;
	
	@Column(name = "TGTDISCI_ID", length = 30 )
	private String tgtdisciId;	
	
	@Column(name = "RELATIONNAME", length = 20 )
    private String relationname;

	@Column(name = "DATASRC", length = 100 )
	private String datasrc;
	
	@Column(name = "SRCDATASRCKEY1", length = 100 )
	private String srcdatasrckey1;
	
	@Column(name = "SRCDATASRCKEY2", length = 100 )
	private String srcdatasrckey2;
	
	@Column(name = "TGTDATASRCKEY1", length = 100 )
	private String tgtdatasrckey1;
	
	@Column(name = "TGTDATASRCKEY2", length = 100 )
	private String tgtdatasrckey2;

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
	private String ciId;

	@Transient
	private Long srcdisId;

	@Transient
	private Long tgtdisId;

	@Transient
	private String srcdisciNm;

	@Transient
	private String tgtdisciNm;

	@Transient
	private String srcdisciclass;

	@Transient
	private String tgtdisciclass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrcdisciId() {
		return srcdisciId;
	}

	public void setSrcdisciId(String srcdisciId) {
		this.srcdisciId = srcdisciId;
	}

	public String getTgtdisciId() {
		return tgtdisciId;
	}

	public void setTgtdisciId(String tgtdisciId) {
		this.tgtdisciId = tgtdisciId;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public String getSrcdatasrckey1() {
		return srcdatasrckey1;
	}

	public void setSrcdatasrckey1(String srcdatasrckey1) {
		this.srcdatasrckey1 = srcdatasrckey1;
	}

	public String getSrcdatasrckey2() {
		return srcdatasrckey2;
	}

	public void setSrcdatasrckey2(String srcdatasrckey2) {
		this.srcdatasrckey2 = srcdatasrckey2;
	}

	public String getTgtdatasrckey1() {
		return tgtdatasrckey1;
	}

	public void setTgtdatasrckey1(String tgtdatasrckey1) {
		this.tgtdatasrckey1 = tgtdatasrckey1;
	}

	public String getTgtdatasrckey2() {
		return tgtdatasrckey2;
	}

	public void setTgtdatasrckey2(String tgtdatasrckey2) {
		this.tgtdatasrckey2 = tgtdatasrckey2;
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

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public Long getSrcdisId() {
		return srcdisId;
	}

	public void setSrcdisId(Long srcdisId) {
		this.srcdisId = srcdisId;
	}

	public Long getTgtdisId() {
		return tgtdisId;
	}

	public void setTgtdisId(Long tgtdisId) {
		this.tgtdisId = tgtdisId;
	}

	public String getSrcdisciNm() {
		return srcdisciNm;
	}

	public void setSrcdisciNm(String srcdisciNm) {
		this.srcdisciNm = srcdisciNm;
	}

	public String getTgtdisciNm() {
		return tgtdisciNm;
	}

	public void setTgtdisciNm(String tgtdisciNm) {
		this.tgtdisciNm = tgtdisciNm;
	}

	public String getSrcdisciclass() {
		return srcdisciclass;
	}

	public void setSrcdisciclass(String srcdisciclass) {
		this.srcdisciclass = srcdisciclass;
	}

	public String getTgtdisciclass() {
		return tgtdisciclass;
	}

	public void setTgtdisciclass(String tgtdisciclass) {
		this.tgtdisciclass = tgtdisciclass;
	}
}