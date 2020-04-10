/**
* 위치 조상 엔터티 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Locancestor.TABLE_NAME)
public class Locancestor implements Serializable {

    public static final String TABLE_NAME="LOCANCESTOR";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;

    @Column(name = "ANCESTOR", length = 30 )
    private String ancestor;

    @Column(name = "LOCATION_ID", length = 30 )
    private String locationId;
    
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

	public String getAncestor() {
		return this.ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	public String getLocationId() {
		return this.locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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