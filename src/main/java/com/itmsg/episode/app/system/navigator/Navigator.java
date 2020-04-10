/**
* 메뉴 엔터티 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.navigator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Navigator.TABLE_NAME)
public class Navigator implements Serializable {

    public static final String TABLE_NAME="NAVIGATOR";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
    
    @Column(name = "PARENT", length = 30 )
    private String parent;
    
    @Column(name = "NAVIGATOR_ID", length = 30 )
    private String navigatorId;
    
    @Column(name = "STATUS", length = 10 )
    private String status;

    @Column(name = "TYPE", length = 10 )
    private String type;
    
    @Column(name = "DESCRIPTION", length = 50 )
    private String description;
    
    @Column(name = "HIERARCHYPATH", length = 100 )
    private String hierarchypath;
    
    @Column(name = "NAVIGATORICON", length = 20 )
    private String navigatoricon;

	@Column(name = "NAVIGATORICONSVG" )
    private String navigatoriconsvg;

    @Column(name = "ORDER_SEQ")
    private Long orderSeq;    

	@Column(name = "URL")
    @Transient   
    private String url;

	@Column(name = "NAME")
    @Transient   
    private String name;

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
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getNavigatorId() {
		return this.navigatorId;
	}

	public void setNavigatorId(String navigatorId) {
		this.navigatorId = navigatorId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHierarchypath() {
		return this.hierarchypath;
	}

	public void setHierarchypath(String hierarchypath) {
		this.hierarchypath = hierarchypath;
	}

	public String getNavigatoricon() {
		return this.navigatoricon;
	}

	public void setNavigatoricon(String navigatoricon) {
		this.navigatoricon = navigatoricon;
	}

	public String getNavigatoriconsvg() {
		return this.navigatoriconsvg;
	}

	public void setNavigatoriconsvg(String navigatoriconsvg) {
		this.navigatoriconsvg = navigatoriconsvg;
	}
	
	public Long getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
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