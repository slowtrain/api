/**
* 분류 사용 오브젝트 엔터티 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.classification;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Classusewith.TABLE_NAME)
public class Classusewith implements Serializable {

    public static final String TABLE_NAME="CLASSUSEWITH";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ORG_ID", length = 20 )
    private String orgId;
        
    @Column(name = "CLASSSTRUCTURE_ID", length = 30 )
    private String classstructureId;
    
    @Column(name = "OBJECT", length = 20 )
    private String object;

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

	public String getClassstructureId() {
		return this.classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getObject() {
		return this.object;
	}

	public void setObject(String object) {
		this.object = object;
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