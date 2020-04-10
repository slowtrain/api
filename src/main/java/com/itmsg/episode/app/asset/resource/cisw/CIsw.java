/**
* 소프트웨어 목록 엔터티 클래스
* @author   최영훈
* @since    2019.08.29
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.29   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.resource.cisw;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = CIsw.TABLE_NAME)
public class CIsw implements Serializable {
	
    public static final String TABLE_NAME="CISW";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
	
	@Column(name = "CI_ID", length = 30 )
    private String ciId;
    
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
	
	@Column(name = "SWCAT_ID")
	private String swcatId;
	
	@Column(name = "START_DT")
	private Date startDt;
	
	@Column(name = "END_DT")
	private Date endDt;
	
	@Column(name = "DISCOVERYPATH")
	private String discoverypath;
	
	@Column(name = "LICNUSECAUSE")
	private String licnusecause;

	@Column(name = "ISPRESENT")
	private Boolean ispresent;

	@Column(name = "DETAILEDVERSION")
	private String detailedversion;
	
	@Column(name = "USERDEFINE")
	private Boolean userdefine;
	
	@Column(name = "ISLICNOUSE")
	private Boolean islicnouse;
	
	@Column(name = "DATASRC")
	private String datasrc;
	
	@Column(name = "DATASRCKEY1")
	private String datasrckey1;
	
	@Column(name = "DATASRCKEY2")
	private String datasrckey2;
	
	@Column(name = "DATASRCPKEY1")
	private String datasrcpkey1;
	
	@Column(name = "DATASRCPKEY2")
	private String datasrcpkey2;
	
	@Column(name = "DATASRCVER")
	private String datasrcver;
	
	@Column(name = "ISUPDATE")
	private Boolean isupdate;

	@Column(name = "CISW_ID")
	private String ciswId;

	@Transient
	@Column(name = "CI_CLASS")	
	private String ciClass;
	
	@Transient
	@Column(name = "OS_NAME")	
	private String osName;
	
	@Transient
	@Column(name = "SW_CLASS")	
	private String swClass;
	
	@Transient
	@Column(name = "PUBLISHERNAME")	
	private String publishername;
	
	@Transient
	@Column(name = "SWCATNAME")	
	private String swcatname;
	
	@Transient
	@Column(name = "SWCATVERSION")	
	private String swcatversion;
	
	@Transient
	@Column(name = "LOCATION_ID")	
	private String locationId;

	@Transient
	@Column(name = "CI_NAME")	
	private String ciName;

	@Transient
	@Column(name = "USESERVERCNT")	
	private BigDecimal useservercnt;

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

	public String getSwcatId() {
		return swcatId;
	}

	public void setSwcatId(String swcatId) {
		this.swcatId = swcatId;
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

	public String getDiscoverypath() {
		return discoverypath;
	}

	public void setDiscoverypath(String discoverypath) {
		this.discoverypath = discoverypath;
	}

	public String getLicnusecause() {
		return licnusecause;
	}

	public void setLicnusecause(String licnusecause) {
		this.licnusecause = licnusecause;
	}

	public Boolean getIspresent() {
		return ispresent;
	}

	public void setIspresent(Boolean ispresent) {
		this.ispresent = ispresent;
	}

	public String getDetailedversion() {
		return detailedversion;
	}

	public void setDetailedversion(String detailedversion) {
		this.detailedversion = detailedversion;
	}

	public Boolean getUserdefine() {
		return userdefine;
	}

	public void setUserdefine(Boolean userdefine) {
		this.userdefine = userdefine;
	}

	public Boolean getIslicnouse() {
		return islicnouse;
	}

	public void setIslicnouse(Boolean islicnouse) {
		this.islicnouse = islicnouse;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public String getDatasrckey1() {
		return datasrckey1;
	}

	public void setDatasrckey1(String datasrckey1) {
		this.datasrckey1 = datasrckey1;
	}

	public String getDatasrckey2() {
		return datasrckey2;
	}

	public void setDatasrckey2(String datasrckey2) {
		this.datasrckey2 = datasrckey2;
	}

	public String getDatasrcpkey1() {
		return datasrcpkey1;
	}

	public void setDatasrcpkey1(String datasrcpkey1) {
		this.datasrcpkey1 = datasrcpkey1;
	}

	public String getDatasrcpkey2() {
		return datasrcpkey2;
	}

	public void setDatasrcpkey2(String datasrcpkey2) {
		this.datasrcpkey2 = datasrcpkey2;
	}

	public String getDatasrcver() {
		return datasrcver;
	}

	public void setDatasrcver(String datasrcver) {
		this.datasrcver = datasrcver;
	}

	public Boolean getIsupdate() {
		return isupdate;
	}

	public void setIsupdate(Boolean isupdate) {
		this.isupdate = isupdate;
	}

	public String getCiswId() {
		return ciswId;
	}

	public void setCiswId(String ciswId) {
		this.ciswId = ciswId;
	}

	public String getCiClass() {
		return ciClass;
	}

	public void setCiClass(String ciClass) {
		this.ciClass = ciClass;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getSwClass() {
		return swClass;
	}

	public void setSwClass(String swClass) {
		this.swClass = swClass;
	}

	public String getPublishername() {
		return publishername;
	}

	public void setPublishername(String publishername) {
		this.publishername = publishername;
	}

	public String getSwcatname() {
		return swcatname;
	}

	public void setSwcatname(String swcatname) {
		this.swcatname = swcatname;
	}

	public String getSwcatversion() {
		return swcatversion;
	}

	public void setSwcatversion(String swcatversion) {
		this.swcatversion = swcatversion;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}

	public BigDecimal getUseservercnt() {
		return useservercnt;
	}

	public void setUseservercnt(BigDecimal useservercnt) {
		this.useservercnt = useservercnt;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}
}