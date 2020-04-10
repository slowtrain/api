/**
* IT자원 엔터티 클래스
* @author   최영훈
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.ci;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import com.itmsg.episode.app.asset.resource.cisw.CIsw;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = CI.TABLE_NAME)
public class CI implements Serializable, Cloneable{
     
    public static final String TABLE_NAME="CI";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "ORG_ID")
    private String orgId;
        
    @Column(name = "CI_ID")
	private String ciId;

	@Column(name = "CINAME")
	private String ciname;
	
	@Column(name = "CLASSSTRUCTURE_ID")
    private String classstructureId;

    @Column(name = "STATUS")
	private String status;
	
	@Column(name = "TYPE")
    private String type;
    
    @Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "LOCATION_ID")
	private String locationId;

	@Column(name = "MANAGER_ID")
	private String managerId;
		
	@Column(name = "USERDEFINE")
	private Boolean userdefine;

	@Column(name = "ISDELETE")
	private Boolean isdelete;

    @Column(name = "EXTALN1")
    private String extaln1;
    
    @Column(name = "EXTALN2")
    private String extaln2;
    
    @Column(name = "EXTALN3")
    private String extaln3;
    
    @Column(name = "EXTALN4")
    private String extaln4;
    
    @Column(name = "EXTALN5")
    private String extaln5;
    
    @Column(name = "EXTALN6")
    private String extaln6;
    
    @Column(name = "EXTALN7")
    private String extaln7;
    
    @Column(name = "EXTALN8")
    private String extaln8;
    
    @Column(name = "EXTALN9")
    private String extaln9;
    
    @Column(name = "EXTALN10")
    private String extaln10;

    @Column(name = "EXTALN11")
    private String extaln11;
    
    @Column(name = "EXTALN12")
    private String extaln12;
    
    @Column(name = "EXTALN13")
    private String extaln13;
    
    @Column(name = "EXTALN14")
    private String extaln14;
    
    @Column(name = "EXTALN15")
    private String extaln15;
    
    @Column(name = "EXTALN16")
    private String extaln16;
    
    @Column(name = "EXTALN17")
    private String extaln17;
    
    @Column(name = "EXTALN18")
    private String extaln18;
    
    @Column(name = "EXTALN19")
    private String extaln19;
    
    @Column(name = "EXTALN20")
    private String extaln20;
    
    @Column(name = "EXTNUM1")
    private BigDecimal extnum1;
    
    @Column(name = "EXTNUM2")
    private BigDecimal extnum2;
    
    @Column(name = "EXTNUM3")
    private BigDecimal extnum3;

    @Column(name = "EXTNUM4")
    private BigDecimal extnum4;
    
    @Column(name = "EXTNUM5")
    private BigDecimal extnum5;
    
    @Column(name = "EXTDATE1")
    private Date extdate1;
    
    @Column(name = "EXTDATE2")
    private Date extdate2;
    
    @Column(name = "EXTDATE3")
    private Date extdate3;

    @Column(name = "EXTDATE4")
    private Date extdate4;
    
    @Column(name = "EXTDATE5")
	private Date extdate5;
	
	@Column(name = "EXTBOOL1")
	private Boolean extbool1;
	
	@Column(name = "EXTBOOL2")
	private Boolean extbool2;
	
	@Column(name = "EXTBOOL3")
	private Boolean extbool3;
	
	@Column(name = "EXTBOOL4")
	private Boolean extbool4;
	
	@Column(name = "EXTBOOL5")
	private Boolean extbool5;

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
	private Boolean chk;
	
	@Transient
	private String relationname;

	@Transient	
	private String managerNm;

	@Transient	
	private String createNm;
	
	@Transient	
	private String updateNm;
	
	@Transient	
	private String classType;

	@Transient	
	private String hierarchypath;

	@Transient
	@Column(name = "CLASSSTRUCTURE_NM")
	private String classstructureNm;

	@Transient
	@Column(name = "ISBFI")
	private Boolean isbfi;
	
	@Transient
	@Column(name = "ISTADDM")
	private Boolean istaddm;
	
	@Transient
	@Column(name = "ISWHATCHALL")
	private Boolean iswhatchall;

	@Transient
	@Column(name = "HARDWAREIP")
	private String hardwareip;

	@Transient
	@Column(name = "SOFTWAREIP")
	private String softwareip;

	@Transient
	private List<CIspec> specList;

	@Transient
	private List<CIrelation> relationList;
	
	@Transient
	private List<CIsw> relationSwList;

	@Transient
	private List<Rparespmap> rparespmapList;

	@Transient
	private String emsId;
	
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Boolean getUserdefine() {
		return userdefine;
	}

	public void setUserdefine(Boolean userdefine) {
		this.userdefine = userdefine;
	}

	public String getExtaln1() {
		return extaln1;
	}

	public void setExtaln1(String extaln1) {
		this.extaln1 = extaln1;
	}

	public String getExtaln2() {
		return extaln2;
	}

	public void setExtaln2(String extaln2) {
		this.extaln2 = extaln2;
	}

	public String getExtaln3() {
		return extaln3;
	}

	public void setExtaln3(String extaln3) {
		this.extaln3 = extaln3;
	}

	public String getExtaln4() {
		return extaln4;
	}

	public void setExtaln4(String extaln4) {
		this.extaln4 = extaln4;
	}

	public String getExtaln5() {
		return extaln5;
	}

	public void setExtaln5(String extaln5) {
		this.extaln5 = extaln5;
	}

	public String getExtaln6() {
		return extaln6;
	}

	public void setExtaln6(String extaln6) {
		this.extaln6 = extaln6;
	}

	public String getExtaln7() {
		return extaln7;
	}

	public void setExtaln7(String extaln7) {
		this.extaln7 = extaln7;
	}

	public String getExtaln8() {
		return extaln8;
	}

	public void setExtaln8(String extaln8) {
		this.extaln8 = extaln8;
	}

	public String getExtaln9() {
		return extaln9;
	}

	public void setExtaln9(String extaln9) {
		this.extaln9 = extaln9;
	}

	public String getExtaln10() {
		return extaln10;
	}

	public void setExtaln10(String extaln10) {
		this.extaln10 = extaln10;
	}

	public String getExtaln11() {
		return extaln11;
	}

	public void setExtaln11(String extaln11) {
		this.extaln11 = extaln11;
	}

	public String getExtaln12() {
		return extaln12;
	}

	public void setExtaln12(String extaln12) {
		this.extaln12 = extaln12;
	}

	public String getExtaln13() {
		return extaln13;
	}

	public void setExtaln13(String extaln13) {
		this.extaln13 = extaln13;
	}

	public String getExtaln14() {
		return extaln14;
	}

	public void setExtaln14(String extaln14) {
		this.extaln14 = extaln14;
	}

	public String getExtaln15() {
		return extaln15;
	}

	public void setExtaln15(String extaln15) {
		this.extaln15 = extaln15;
	}

	public String getExtaln16() {
		return extaln16;
	}

	public void setExtaln16(String extaln16) {
		this.extaln16 = extaln16;
	}

	public String getExtaln17() {
		return extaln17;
	}

	public void setExtaln17(String extaln17) {
		this.extaln17 = extaln17;
	}

	public String getExtaln18() {
		return extaln18;
	}

	public void setExtaln18(String extaln18) {
		this.extaln18 = extaln18;
	}

	public String getExtaln19() {
		return extaln19;
	}

	public void setExtaln19(String extaln19) {
		this.extaln19 = extaln19;
	}

	public String getExtaln20() {
		return extaln20;
	}

	public void setExtaln20(String extaln20) {
		this.extaln20 = extaln20;
	}

	public BigDecimal getExtnum1() {
		return extnum1;
	}

	public void setExtnum1(BigDecimal extnum1) {
		this.extnum1 = extnum1;
	}

	public BigDecimal getExtnum2() {
		return extnum2;
	}

	public void setExtnum2(BigDecimal extnum2) {
		this.extnum2 = extnum2;
	}

	public BigDecimal getExtnum3() {
		return extnum3;
	}

	public void setExtnum3(BigDecimal extnum3) {
		this.extnum3 = extnum3;
	}

	public BigDecimal getExtnum4() {
		return extnum4;
	}

	public void setExtnum4(BigDecimal extnum4) {
		this.extnum4 = extnum4;
	}

	public BigDecimal getExtnum5() {
		return extnum5;
	}

	public void setExtnum5(BigDecimal extnum5) {
		this.extnum5 = extnum5;
	}

	public Date getExtdate1() {
		return extdate1;
	}

	public void setExtdate1(Date extdate1) {
		this.extdate1 = extdate1;
	}

	public Date getExtdate2() {
		return extdate2;
	}

	public void setExtdate2(Date extdate2) {
		this.extdate2 = extdate2;
	}

	public Date getExtdate3() {
		return extdate3;
	}

	public void setExtdate3(Date extdate3) {
		this.extdate3 = extdate3;
	}

	public Date getExtdate4() {
		return extdate4;
	}

	public void setExtdate4(Date extdate4) {
		this.extdate4 = extdate4;
	}

	public Date getExtdate5() {
		return extdate5;
	}

	public void setExtdate5(Date extdate5) {
		this.extdate5 = extdate5;
	}

	public Boolean getExtbool1() {
		return extbool1;
	}

	public void setExtbool1(Boolean extbool1) {
		this.extbool1 = extbool1;
	}

	public Boolean getExtbool2() {
		return extbool2;
	}

	public void setExtbool2(Boolean extbool2) {
		this.extbool2 = extbool2;
	}

	public Boolean getExtbool3() {
		return extbool3;
	}

	public void setExtbool3(Boolean extbool3) {
		this.extbool3 = extbool3;
	}

	public Boolean getExtbool4() {
		return extbool4;
	}

	public void setExtbool4(Boolean extbool4) {
		this.extbool4 = extbool4;
	}

	public Boolean getExtbool5() {
		return extbool5;
	}

	public void setExtbool5(Boolean extbool5) {
		this.extbool5 = extbool5;
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

	public Boolean getChk() {
		return chk;
	}

	public void setChk(Boolean chk) {
		this.chk = chk;
	}
	
	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getManagerNm() {
		return managerNm;
	}

	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
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

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getHierarchypath() {
		return hierarchypath;
	}

	public void setHierarchypath(String hierarchypath) {
		this.hierarchypath = hierarchypath;
	}

	public Boolean getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
	}
	
	public String getClassstructureNm() {
		return this.classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}

	public Boolean getIsbfi() {
		return isbfi;
	}

	public void setIsbfi(Boolean isbfi) {
		this.isbfi = isbfi;
	}

	public Boolean getIstaddm() {
		return istaddm;
	}

	public void setIstaddm(Boolean istaddm) {
		this.istaddm = istaddm;
	}

	public Boolean getIswhatchall() {
		return iswhatchall;
	}

	public void setIswhatchall(Boolean iswhatchall) {
		this.iswhatchall = iswhatchall;
	}

	public List<CIspec> getSpecList() {
		return specList;
	}

	public void setSpecList(List<CIspec> specList) {
		this.specList = specList;
	}

	public List<CIrelation> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<CIrelation> relationList) {
		this.relationList = relationList;
	}

	public List<CIsw> getRelationSwList() {
		return relationSwList;
	}

	public void setRelationSwList(List<CIsw> relationSwList) {
		this.relationSwList = relationSwList;
	}

	public List<Rparespmap> getRparespmapList() {
		return rparespmapList;
	}

	public void setRparespmapList(List<Rparespmap> rparespmapList) {
		this.rparespmapList = rparespmapList;
	}

	public String getEmsId() {
		return emsId;
	}

	public void setEmsId(String emsId) {
		this.emsId = emsId;
	}

	public String getHardwareip() {
		return hardwareip;
	}

	public void setHardwareip(String hardwareip) {
		this.hardwareip = hardwareip;
	}

	public String getSoftwareip() {
		return softwareip;
	}

	public void setSoftwareip(String softwareip) {
		this.softwareip = softwareip;
	}

}