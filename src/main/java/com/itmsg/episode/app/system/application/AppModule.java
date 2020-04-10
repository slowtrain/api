/**
* 어플리케이션 모듈 엔터티 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = AppModule.TABLE_NAME)
public class AppModule {

    public static final String TABLE_NAME="APPMODULE";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppModule.TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = AppModule.TABLE_NAME+"_SEQ", sequenceName = AppModule.TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "APP_ID")
    @NotNull
    private String appId;

    @Column(name = "MODULE_ID")
    @NotNull
    private String module_id;

    @Transient
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

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getModule_id() {
		return this.module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
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