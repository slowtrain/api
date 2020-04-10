/**
*  유저 그룹 조상 엔터티 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = UserGroup.TABLE_NAME)
public class UserGroupAncestor implements Serializable {

    public static final String TABLE_NAME="USERGROUPANCESTOR";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;

    @Column(name = "ANCESTOR", length = 20 )
    @Size(min = 4, max = 20)
    private String ANCESTOR;

    @Column(name = "USERGROUP_ID", length = 20 )
    @Size(min = 4, max = 20)
    private String usergroupId;

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

    @Column(name = "CREATE_ID")
    private String createId;

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

	public String getANCESTOR() {
		return this.ANCESTOR;
	}

	public void setANCESTOR(String ANCESTOR) {
		this.ANCESTOR = ANCESTOR;
	}

	public String getUsergroupId() {
		return this.usergroupId;
	}

	public void setUsergroupId(String usergroupId) {
		this.usergroupId = usergroupId;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
}