package com.itmsg.episode.integration.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = IFUser.TABLE_NAME)
public class IFUser implements Serializable {

	private static final long serialVersionUID = 2302894476361333252L;

	public static final String TABLE_NAME = "if_user";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IFUser.TABLE_NAME + "_SEQ")
	@SequenceGenerator(name = IFUser.TABLE_NAME + "_SEQ", sequenceName = IFUser.TABLE_NAME+ "_SEQ", allocationSize = 1)
    private Long id;
	
	@Column(name = "SOURCESYSTEM")
    @NotNull
	private String sourceSystem;

	@Column(name = "TARGETSYSTEM")
    @NotNull
	private String targetSystem;

	@Column(name = "ACTIONFLAG")
    @NotNull
	private String actionFlag;

	@Column(name = "RESULT")
	private String result;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "COL1")
	private String col1;

	@Column(name = "COL2")
	private String col2;

	@Column(name = "COL3")
	private String col3;

	@Column(name = "COL4")
	private String col4;

	@Column(name = "COL5")
	private String col5;

	@Column(name = "COL6")
	private String col6;

	@Column(name = "COL7")
	private String col7;

	@Column(name = "COL8")
	private String col8;

	@Column(name = "COL9")
	private String col9;

	@Column(name = "COL10")
	private String col10;

	@Column(name = "CREATE_ID")
    private String createId;

	@Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	
     

 


}
