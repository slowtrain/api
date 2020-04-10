/**
* 유저 엔터티 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name =User.TABLE_NAME)
public class User implements Serializable{

    public static final String TABLE_NAME="ESUSER";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = User.TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = User.TABLE_NAME+"_SEQ", sequenceName = User.TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRST_NAME", length = 50)
    @NotNull
    private String firstname;

    @Column(name = "LAST_NAME", length = 50)
    private String lastname;

    @Column(name = "ORG_ID", length = 50)
    @NotNull
    private String orgId;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    private String email;

    @Column(name = "TEL", length = 50)
    private String tel;

    @Column(name = "MOBILE", length = 50)
    private String mobile;

    @Column(name = "ADDRESS", length = 50)
    private String address;

    @Column(name = "STATUS", length = 50)
    @NotNull
	private String status;
	
	@Column(name = "DEFAULTGROUP")
	private String defaultGroup;

    @Column(name = "LOGIN_DT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date logindt;

    @Column(name = "PASSWORD_RESET_DT")
    @CreationTimestamp
    private Date passwordResetDt;

    @Column(name = "LANG", length = 20)
    @NotNull
    private String lang;

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
	
	@Column(name = "CHANGEPASSWORD")
	private Boolean changepassword;

	@Transient
	@Column(name = "CREATE_NM")
    private String createNm;
 
	@Transient
    @Column(name = "UPDATE_NM")
	private String updateNm;

	@Transient
	private String userfullname;

	@Transient
	private Long groupUserId;

	@Transient
	private Long roleUserId;

	@Transient
	private Boolean userChk;

	@Transient
	private Boolean chk;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(String defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public Date getLogindt() {
		return this.logindt;
	}

	public void setLogindt(Date logindt) {
		this.logindt = logindt;
	}

	public Date getPasswordResetDt() {
		return this.passwordResetDt;
	}

	public void setPasswordResetDt(Date passwordResetDt) {
		this.passwordResetDt = passwordResetDt;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public String getUserfullname() {
		return this.userfullname;
	}

	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}

	public Boolean getChk() {
		return chk;
	}

	public void setChk(Boolean chk) {
		this.chk = chk;
	}

	public Boolean getChangepassword() {
		return changepassword;
	}

	public void setChangepassword(Boolean changepassword) {
		this.changepassword = changepassword;
	}

	public Long getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(Long groupUserId) {
		this.groupUserId = groupUserId;
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

	public Boolean getUserChk() {
		return userChk;
	}

	public void setUserChk(Boolean userChk) {
		this.userChk = userChk;
	}

	public Long getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(Long roleUserId) {
		this.roleUserId = roleUserId;
	}


}
