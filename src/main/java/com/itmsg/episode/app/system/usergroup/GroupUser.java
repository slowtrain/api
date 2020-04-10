/**
* 그룹 유저 엔터티 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.itmsg.episode.app.system.user.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = GroupUser.TABLE_NAME)
public class GroupUser implements Serializable {

    public static final String TABLE_NAME="GROUPUSER";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;


	@Column(name = "ORG_ID")
    @NotNull
    private String orgId;

    @Column(name = "USERGROUP_ID", length = 20 )
    @NotNull
    private String usergroupId;

    @Column(name = "USERNAME", length = 20 )
    @NotNull
	private String username;
	
	@Column(name = "ISCHIEF")
    private  Boolean isChief;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "USERNAME", referencedColumnName = "USERNAME")
    private User user;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsergroupId() {
		return this.usergroupId;
	}

	public void setUsergroupId(String usergroupId) {
		this.usergroupId = usergroupId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Boolean getIsChief() {
		return isChief;
	}

	public void setIsChief(Boolean isChief) {
		this.isChief = isChief;
	}

	
	

}