/**
* 변환 엔터티 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Conversion.TABLE_NAME)
public class Conversion implements Serializable,Cloneable{

    public static final String TABLE_NAME="CONVERSION";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;
	
	@Column(name = "TABLENAME")
	private String tablename;
	
	@Column(name = "COLUMNNAME")
    private String columnname;
    
    @Column(name = "CLASSSTRUCTURE_ID")
    private String classstructureId;

	@Column(name = "CONVERTER_ID")
	private String converterId;
	    
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

    @Column(name = "CONVERSIONPROCESS")
    private String conversionprocess;

    @Transient
	@Column(name = "CLASSSTRUCTURE_NM")
    private String classstructureNm;
    
    @Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

    @Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getClassstructureId() {
        return classstructureId;
    }

    public void setClassstructureId(String classstructureId) {
        this.classstructureId = classstructureId;
    }

    public String getConverterId() {
        return converterId;
    }

    public void setConverterId(String converterId) {
        this.converterId = converterId;
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

    public String getConversionprocess() {
        return conversionprocess;
    }

    public void setConversionprocess(String conversionprocess) {
        this.conversionprocess = conversionprocess;
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