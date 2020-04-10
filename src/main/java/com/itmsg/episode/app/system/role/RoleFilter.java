/**
* 롤 필터 엔터티 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = RoleFilter.TABLE_NAME)
public class RoleFilter implements Serializable {

    public static final String TABLE_NAME="ROLEFILTER";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ROLE_ID", length = 20 )
    private String roleId;

    @Column(name = "DATAFILTER_ID", length = 20 )
    private String datafilterId;

    @Column(name = "WHERECLAUSE", length = 20 )
    private String whereClause;

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
	private String status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDatafilterId() {
		return this.datafilterId;
	}

	public void setDatafilterId(String datafilterId) {
		this.datafilterId = datafilterId;
	}

    public String getWhereClause() {
		return this.whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}