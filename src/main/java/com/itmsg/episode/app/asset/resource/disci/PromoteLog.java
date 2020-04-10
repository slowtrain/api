package com.itmsg.episode.app.asset.resource.disci;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = PromoteLog.TABLE_NAME)
public class PromoteLog implements Serializable {

    public static final String TABLE_NAME="PROMOTELOG";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(name = "DISCI_ID")
	private String disciId;

	@Column(name = "DATASRC")
	private String datasrc;

	@Column(name = "DESCRIPTION")
	private String description;

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

	public String getDisciId() {
		return disciId;
	}

	public void setDisciId(String disciId) {
		this.disciId = disciId;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
		
	
}