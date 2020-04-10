/**
* 라이선스 엔터티 클래스
* @author   최영훈
* @since    2019.09.16
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.16   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = SwLicense.TABLE_NAME)
@DynamicInsert
public class SwLicense implements Serializable {

    public static final String TABLE_NAME="SWLICENSE";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "ORG_ID", length = 20 )
    private String orgId;
		
	@Column(name = "SWLICENSE_ID", length = 30 )
	private String swlicenseId;
		
	@Column(name = "SWLICNAME", length = 100 )
	private String swlicname;
		
	@Column(name = "SWLICDESCRIPTION", length = 255 )
	private String swlicdescription;
		
	@Column(name = "MANAGER_ID", length = 20 )
	private String managerId;

	@Column(name = "SWLICSTATUS", length = 20 )
	private String swlicstatus;
		
	@Column(name = "PURCHASETYPE", length = 20 )
	private String purchasetype;
		
	@Column(name = "SWLICTYPE", length = 20 )
	private String swlictype;
		
	@Column(name = "COMPANY_ID", length = 30 )
	private String companyId;
		
	@Column(name = "ASSET_ID", length = 30 )
	private String assetId;
		
	@Column(name = "PO_ID", length = 30 )
	private String poId;
		
	@Column(name = "PURCHASE_DT" )
	private Date purchaseDt;
		
	@Column(name = "START_DT" )
	private Date startDt;
		
	@Column(name = "END_DT" )
	private Date endDt;
		
	@Column(name = "PURCHASEPRICE" )
	private BigDecimal purchaseprice;
		
	@Column(name = "PRICEUNIT", length = 10 )
	private String priceunit;
		
	@Column(name = "PURCHASEUNIT", length = 10 )
	private String purchaseunit;
		
	@Column(name = "PURCHASENUM" )
	private Long purchasenum;
		
	@Column(name = "INYEAR" )
	private Long inyear;
		
	@Column(name = "REMAINPRICE" )
	private BigDecimal remainprice;

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
	private String managerNm;
	
	@Transient
	private String createNm;

	@Transient
	private String updateNm;
	
	@Transient
	private Long relId;
	
	@Transient
	private String purchasetypeNm;

	@Transient
	@Column(name = "SWLICTYPE_NM")
	private String swlictypeNm;

	@Transient
	@Column(name = "SWLICTYPE_NM")
	private String companyname;

	@Transient
	private List<SwLiccat> swcatList;

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

	public String getSwlicenseId() {
		return swlicenseId;
	}

	public void setSwlicenseId(String swlicenseId) {
		this.swlicenseId = swlicenseId;
	}

	public String getSwlicname() {
		return swlicname;
	}

	public void setSwlicname(String swlicname) {
		this.swlicname = swlicname;
	}

	public String getSwlicdescription() {
		return swlicdescription;
	}

	public void setSwlicdescription(String swlicdescription) {
		this.swlicdescription = swlicdescription;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getSwlicstatus() {
		return swlicstatus;
	}

	public void setSwlicstatus(String swlicstatus) {
		this.swlicstatus = swlicstatus;
	}

	public String getPurchasetype() {
		return purchasetype;
	}

	public void setPurchasetype(String purchasetype) {
		this.purchasetype = purchasetype;
	}

	public String getSwlictype() {
		return swlictype;
	}

	public void setSwlictype(String swlictype) {
		this.swlictype = swlictype;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public Date getPurchaseDt() {
		return purchaseDt;
	}

	public void setPurchaseDt(Date purchaseDt) {
		this.purchaseDt = purchaseDt;
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

	public BigDecimal getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public String getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(String priceunit) {
		this.priceunit = priceunit;
	}

	public String getPurchaseunit() {
		return purchaseunit;
	}

	public void setPurchaseunit(String purchaseunit) {
		this.purchaseunit = purchaseunit;
	}

	public Long getPurchasenum() {
		return purchasenum;
	}

	public void setPurchasenum(Long purchasenum) {
		this.purchasenum = purchasenum;
	}

	public Long getInyear() {
		return inyear;
	}

	public void setInyear(Long inyear) {
		this.inyear = inyear;
	}

	public BigDecimal getRemainprice() {
		return remainprice;
	}

	public void setRemainprice(BigDecimal remainprice) {
		this.remainprice = remainprice;
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

	public String getManagerNm() {
		return managerNm;
	}

	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
	}

	public String getUpdateNm() {
		return updateNm;
	}

	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}

	public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public String getPurchasetypeNm() {
		return purchasetypeNm;
	}

	public void setPurchasetypeNm(String purchasetypeNm) {
		this.purchasetypeNm = purchasetypeNm;
	}

	public String getSwlictypeNm() {
		return swlictypeNm;
	}

	public void setSwlictypeNm(String swlictypeNm) {
		this.swlictypeNm = swlictypeNm;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public List<SwLiccat> getSwcatList() {
		return swcatList;
	}

	public void setSwcatList(List<SwLiccat> swcatList) {
		this.swcatList = swcatList;
	}

	public String getCreateNm() {
		return createNm;
	}

	public void setCreateNm(String createNm) {
		this.createNm = createNm;
	}
}