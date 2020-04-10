/**
* 소프트웨어 카탈로그 엔터티 클래스
* @author   최영훈
* @since    2019.10.14
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.14   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.swcat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = Swcat.TABLE_NAME)
public class Swcat implements Serializable {

    public static final String TABLE_NAME="SWCAT";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Transient
    @Column(name = "ORG_ID" )
    private String orgId;
	
	@Column(name = "SWCAT_ID")
	private String swcatId;
	
	@Column(name = "CLASSSTRUCTURE_ID")
	private String classstructureId;
	
	@Column(name = "SWCATTYPE")
    private String swcattype;
	
	@Column(name = "PARENT_ID")
	private String parentId;
	
	@Column(name = "SWCATNAME")
	private String swcatname;

	@Column(name = "SWCATDESCRIPTION")
	private String swcatdescription;
	
	@Column(name = "SWCATVERSION")
	private String swcatversion;
	
	@Column(name = "PUBLISHERNAME")
	private String publishername;
	
	@Column(name = "ISDOMESTIC")
	private Boolean isdomestic;

	@Column(name = "ISDELETE")
	private Boolean isdelete;

	@Column(name = "USERDEFINE")
	private Boolean userdefine;
	
	@Column(name = "ISPROMOTETOCI")
	private Boolean ispromotetoci;
	
	@Column(name = "DATASRC")
	private String datasrc;
	
	@Column(name = "DATASRC_GUID")
	private String datasrcGuid;
    
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

	@Column(name = "HIERARCHYPATH")
	private String hierarchypath;

	@Transient
	private Boolean chk;

	@Transient
	@Column(name = "PURCHASEUNIT")
	private String purchaseunit;

	@Transient
	@Column(name = "PURCHASENUM")
	private BigDecimal purchasenum;

	@Transient
	@Column(name = "USENUM")
	private BigDecimal usenum;

	@Transient
	@Column(name = "REMAINNUM")
	private BigDecimal remainnum;

	@Transient
	@Column(name = "USESEVER")
	private BigDecimal usesever;

	@Transient
	@Column(name = "RELATIONLICENSE")
	private BigDecimal relationlicense;

	@Transient
	private String relId;

	@Transient
	@Column(name = "CLASSSTRUCTURE_NM")
	private String classstructureNm;

	@Transient
	@Column(name = "CREATE_NM")
	private String createNm;

	@Transient
	@Column(name = "UPDATE_NM")
	private String updateNm;

	@Transient
	@Column(name = "SWCATFULLNAME")
	private String swcatfullname;

	@Transient
	@Column(name = "ISDOMESTIC_NM")
	private String isdomesticNm;

	@Transient
	@Column(name = "ISPROMOTETOCI_NM")
	private String ispromotetociNm;

	@Transient
	@Column(name = "GRP_ID")
	private String grpId;

	@Transient
	@Column(name = "START_DT")
	private Date startDt;
	
	@Transient
	@Column(name = "END_DT")
	private Date endDt;

	@Transient
	@Column(name = "ISLICNOUSE")
	private Boolean islicnouse;

	@Transient
	@Column(name = "CISW_ID")
    private Long ciswId;

	@Transient
	@Column(name = "LICNUSECAUSE")
	private String licnusecause;

	@Transient
	@Column(name = "DETAILEDVERSION")
	private String detailedversion;

	@Transient
	@Column(name = "DISCOVERYPATH")
	private String discoverypath;

	@Transient
	private Swcat child;

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

	public String getSwcatId() {
		return swcatId;
	}

	public void setSwcatId(String swcatId) {
		this.swcatId = swcatId;
	}

	public String getClassstructureId() {
		return classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getSwcattype() {
		return swcattype;
	}

	public void setSwcattype(String swcattype) {
		this.swcattype = swcattype;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSwcatname() {
		return swcatname;
	}

	public void setSwcatname(String swcatname) {
		this.swcatname = swcatname;
	}

	public String getSwcatdescription() {
		return swcatdescription;
	}

	public void setSwcatdescription(String swcatdescription) {
		this.swcatdescription = swcatdescription;
	}

	public String getSwcatversion() {
		return swcatversion;
	}

	public void setSwcatversion(String swcatversion) {
		this.swcatversion = swcatversion;
	}

	public String getPublishername() {
		return publishername;
	}

	public void setPublishername(String publishername) {
		this.publishername = publishername;
	}

	public Boolean getIsdomestic() {
		return isdomestic;
	}

	public void setIsdomestic(Boolean isdomestic) {
		this.isdomestic = isdomestic;
	}

	public Boolean getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
	}

	public Boolean getUserdefine() {
		return userdefine;
	}

	public void setUserdefine(Boolean userdefine) {
		this.userdefine = userdefine;
	}

	public Boolean getIspromotetoci() {
		return ispromotetoci;
	}

	public void setIspromotetoci(Boolean ispromotetoci) {
		this.ispromotetoci = ispromotetoci;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public String getDatasrcGuid() {
		return datasrcGuid;
	}

	public void setDatasrcGuid(String datasrcGuid) {
		this.datasrcGuid = datasrcGuid;
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

	public String getHierarchypath() {
		return hierarchypath;
	}

	public void setHierarchypath(String hierarchypath) {
		this.hierarchypath = hierarchypath;
	}

	public Boolean getChk() {
		return chk;
	}

	public void setChk(Boolean chk) {
		this.chk = chk;
	}

	public String getPurchaseunit() {
		return purchaseunit;
	}

	public void setPurchaseunit(String purchaseunit) {
		this.purchaseunit = purchaseunit;
	}

	public BigDecimal getPurchasenum() {
		return purchasenum;
	}

	public void setPurchasenum(BigDecimal purchasenum) {
		this.purchasenum = purchasenum;
	}

	public BigDecimal getUsenum() {
		return usenum;
	}

	public void setUsenum(BigDecimal usenum) {
		this.usenum = usenum;
	}

	public BigDecimal getRemainnum() {
		return remainnum;
	}

	public void setRemainnum(BigDecimal remainnum) {
		this.remainnum = remainnum;
	}

	public BigDecimal getUsesever() {
		return usesever;
	}

	public void setUsesever(BigDecimal usesever) {
		this.usesever = usesever;
	}

	public BigDecimal getRelationlicense() {
		return relationlicense;
	}

	public void setRelationlicense(BigDecimal relationlicense) {
		this.relationlicense = relationlicense;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}

	public String getUpdateNm() {
		return updateNm;
	}

	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}

	public String getSwcatfullname() {
		return swcatfullname;
	}

	public void setSwcatfullname(String swcatfullname) {
		this.swcatfullname = swcatfullname;
	}

	public String getIsdomesticNm() {
		return isdomesticNm;
	}

	public void setIsdomesticNm(String isdomesticNm) {
		this.isdomesticNm = isdomesticNm;
	}

	public String getIspromotetociNm() {
		return ispromotetociNm;
	}

	public void setIspromotetociNm(String ispromotetociNm) {
		this.ispromotetociNm = ispromotetociNm;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Boolean getIslicnouse() {
		return islicnouse;
	}

	public void setIslicnouse(Boolean islicnouse) {
		this.islicnouse = islicnouse;
	}

	public Long getCiswId() {
		return ciswId;
	}

	public void setCiswId(Long ciswId) {
		this.ciswId = ciswId;
	}

	public String getLicnusecause() {
		return licnusecause;
	}

	public void setLicnusecause(String licnusecause) {
		this.licnusecause = licnusecause;
	}

	public Swcat getChild() {
		return child;
	}

	public void setChild(Swcat child) {
		this.child = child;
	}

	public String getCreateNm() {
		return createNm;
	}

	public void setCreateNm(String createNm) {
		this.createNm = createNm;
	}

	public String getDetailedversion() {
		return detailedversion;
	}

	public void setDetailedversion(String detailedversion) {
		this.detailedversion = detailedversion;
	}

	public String getDiscoverypath() {
		return discoverypath;
	}

	public void setDiscoverypath(String discoverypath) {
		this.discoverypath = discoverypath;
	}
}