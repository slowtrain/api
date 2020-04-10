/**
* 분류 엔터티 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = Classstructure.TABLE_NAME)
public class Classstructure implements Serializable {

    public static final String TABLE_NAME="CLASSSTRUCTURE";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
    
    @Column(name = "PARENT", length = 30 )
    private String parent;
    
    @Column(name = "CLASSSTRUCTURE_ID", length = 30 )
    private String classstructureId;

    @Column(name = "STATUS", length = 10 )
    private String status;
    
    @Column(name = "DESCRIPTION", length = 50 )
    private String description;
    
    @Column(name = "HIERARCHYPATH", length = 100 )
    private String hierarchypath;

    @Column(name = "CLASSICON", length = 20 )
    private String classicon;
    
    @Column(name = "EXTALN1", length = 20 )
    private String extaln1;
    
    @Column(name = "EXTALN2", length = 20 )
    private String extaln2;
    
    @Column(name = "EXTALN3", length = 20 )
    private String extaln3;
    
    @Column(name = "EXTALN4", length = 20 )
    private String extaln4;
    
    @Column(name = "EXTALN5", length = 20 )
    private String extaln5;
    
    @Column(name = "EXTALN6", length = 20 )
    private String extaln6;
    
    @Column(name = "EXTALN7", length = 20 )
    private String extaln7;
    
    @Column(name = "EXTALN8", length = 20 )
    private String extaln8;
    
    @Column(name = "EXTALN9", length = 20 )
    private String extaln9;
    
    @Column(name = "EXTALN10", length = 20 )
    private String extaln10;

    @Column(name = "EXTALN11", length = 20 )
    private String extaln11;
    
    @Column(name = "EXTALN12", length = 20 )
    private String extaln12;
    
    @Column(name = "EXTALN13", length = 20 )
    private String extaln13;
    
    @Column(name = "EXTALN14", length = 20 )
    private String extaln14;
    
    @Column(name = "EXTALN15", length = 20 )
    private String extaln15;
    
    @Column(name = "EXTALN16", length = 20 )
    private String extaln16;
    
    @Column(name = "EXTALN17", length = 20 )
    private String extaln17;
    
    @Column(name = "EXTALN18", length = 20 )
    private String extaln18;
    
    @Column(name = "EXTALN19", length = 20 )
    private String extaln19;
    
    @Column(name = "EXTALN20", length = 20 )
    private String extaln20;
    
    @Column(name = "EXTNUM1", length = 20 )
    private String extnum1;
    
    @Column(name = "EXTNUM2", length = 20 )
    private String extnum2;
    
    @Column(name = "EXTNUM3", length = 20 )
    private String extnum3;

    @Column(name = "EXTNUM4", length = 20 )
    private String extnum4;
    
    @Column(name = "EXTNUM5", length = 20 )
    private String extnum5;
    
    @Column(name = "EXTDATE1", length = 20 )
    private String extdate1;
    
    @Column(name = "EXTDATE2", length = 20 )
    private String extdate2;
    
    @Column(name = "EXTDATE3", length = 20 )
    private String extdate3;

    @Column(name = "EXTDATE4", length = 20 )
    private String extdate4;
    
    @Column(name = "EXTDATE5", length = 20 )
    private String extdate5;

	@Column(name = "EXTBOOL1", length = 20 )
	private String extbool1;
	
	@Column(name = "EXTBOOL2", length = 20 )
	private String extbool2;
	
	@Column(name = "EXTBOOL3", length = 20 )
	private String extbool3;
	
	@Column(name = "EXTBOOL4", length = 20 )
	private String extbool4;
	
	@Column(name = "EXTBOOL5", length = 20 )
	private String extbool5;

	@Column(name = "CLASSICONSVG")
	private String classiconsvg;

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

	@Column(name = "DATASRC")
	private String datasrc;
		
	@Transient
	@Column(name = "CHK")
	private Boolean chk;

	@Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

	@Transient
	@Column(name = "CLASSSTRUCTURE_NM")
    private String classstructureNm;

	@Transient
	private List<Classspec> specDataList;

	@Transient
	private List<Classusewith> usewithDataList;

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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHierarchypath() {
		return hierarchypath;
	}

	public void setHierarchypath(String hierarchypath) {
		this.hierarchypath = hierarchypath;
	}

	public String getClassicon() {
		return classicon;
	}

	public void setClassicon(String classicon) {
		this.classicon = classicon;
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

	public String getExtnum1() {
		return extnum1;
	}

	public void setExtnum1(String extnum1) {
		this.extnum1 = extnum1;
	}

	public String getExtnum2() {
		return extnum2;
	}

	public void setExtnum2(String extnum2) {
		this.extnum2 = extnum2;
	}

	public String getExtnum3() {
		return extnum3;
	}

	public void setExtnum3(String extnum3) {
		this.extnum3 = extnum3;
	}

	public String getExtnum4() {
		return extnum4;
	}

	public void setExtnum4(String extnum4) {
		this.extnum4 = extnum4;
	}

	public String getExtnum5() {
		return extnum5;
	}

	public void setExtnum5(String extnum5) {
		this.extnum5 = extnum5;
	}

	public String getExtdate1() {
		return extdate1;
	}

	public void setExtdate1(String extdate1) {
		this.extdate1 = extdate1;
	}

	public String getExtdate2() {
		return extdate2;
	}

	public void setExtdate2(String extdate2) {
		this.extdate2 = extdate2;
	}

	public String getExtdate3() {
		return extdate3;
	}

	public void setExtdate3(String extdate3) {
		this.extdate3 = extdate3;
	}

	public String getExtdate4() {
		return extdate4;
	}

	public void setExtdate4(String extdate4) {
		this.extdate4 = extdate4;
	}

	public String getExtdate5() {
		return extdate5;
	}

	public void setExtdate5(String extdate5) {
		this.extdate5 = extdate5;
	}

	public String getExtbool1() {
		return extbool1;
	}

	public void setExtbool1(String extbool1) {
		this.extbool1 = extbool1;
	}

	public String getExtbool2() {
		return extbool2;
	}

	public void setExtbool2(String extbool2) {
		this.extbool2 = extbool2;
	}

	public String getExtbool3() {
		return extbool3;
	}

	public void setExtbool3(String extbool3) {
		this.extbool3 = extbool3;
	}

	public String getExtbool4() {
		return extbool4;
	}

	public void setExtbool4(String extbool4) {
		this.extbool4 = extbool4;
	}

	public String getExtbool5() {
		return extbool5;
	}

	public void setExtbool5(String extbool5) {
		this.extbool5 = extbool5;
	}

	public String getClassiconsvg() {
		return classiconsvg;
	}

	public void setClassiconsvg(String classiconsvg) {
		this.classiconsvg = classiconsvg;
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

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public Boolean getChk() {
		return chk;
	}

	public void setChk(Boolean chk) {
		this.chk = chk;
	}

	public List<Classspec> getSpecDataList() {
		return specDataList;
	}

	public void setSpecDataList(List<Classspec> specDataList) {
		this.specDataList = specDataList;
	}

	public List<Classusewith> getUsewithDataList() {
		return usewithDataList;
	}

	public void setUsewithDataList(List<Classusewith> usewithDataList) {
		this.usewithDataList = usewithDataList;
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

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}
}