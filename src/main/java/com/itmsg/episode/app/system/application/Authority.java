/**
* 어플리케이션 권한 엔터티 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name =Authority.TABLE_NAME)
public class Authority implements Serializable{
    
    public static final String TABLE_NAME="AUTHORITY";
    
    @Id
    @Column(name = "ID")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Authority.TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = Authority.TABLE_NAME+"_SEQ", sequenceName = Authority.TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "AUTHORITY_ID")
    @NotNull
    private String authorityId;

    @Column(name = "APP_ID")
    @NotNull
    private String appId;

    @Column(name = "DESCRIPTION")
    @NotNull
    private String description;

    
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

	public String getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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