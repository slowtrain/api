package com.itmsg.episode.app.asset.resource.disci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = DisCIspec.TABLE_NAME)
public class DisCIspec implements Serializable {

    public static final String TABLE_NAME="DISCISPEC";

    @Id
    @Column(name = "ID")
    private Long id;
        
    @Column(name = "DISCI_ID", length = 30 )
	private String disciId;
		
	@Column(name = "DISCLASSSPEC_ID", length = 30 )
    private String disclassspecId;

    @Column(name = "SPECTYPE", length = 30 )
    private String spectype;
    
    @Column(name = "ALNVALUE", length = 100 )
	private String alnvalue;

	@Column(name = "NUMVALUE" )
    private BigDecimal numvalue;

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

    public String getDisciId() {
        return disciId;
    }

    public void setDisciId(String disciId) {
        this.disciId = disciId;
    }

    public String getDisclassspecId() {
        return disclassspecId;
    }

    public void setDisclassspecId(String disclassspecId) {
        this.disclassspecId = disclassspecId;
    }

    public String getSpectype() {
        return spectype;
    }

    public void setSpectype(String spectype) {
        this.spectype = spectype;
    }

    public String getAlnvalue() {
        return alnvalue;
    }

    public void setAlnvalue(String alnvalue) {
        this.alnvalue = alnvalue;
    }

    public BigDecimal getNumvalue() {
        return numvalue;
    }

    public void setNumvalue(BigDecimal numvalue) {
        this.numvalue = numvalue;
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