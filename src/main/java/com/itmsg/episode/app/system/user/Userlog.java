/**
* 유저 로그 엔터티 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name =Userlog.TABLE_NAME)
public class Userlog implements Serializable{

    public static final String TABLE_NAME="USERLOG";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Userlog.TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = Userlog.TABLE_NAME+"_SEQ", sequenceName = Userlog.TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "USER_ID")
	private String	userId;
	
	@Column(name = "CLIENTIP")
	private String	clientip;

	@Column(name = "LOGTYPE")
	private String	logtype;

	@Column(name = "LOGDESCRIPTION")
	private String	logdescription;

	@Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientip() {
		return clientip;
	}

	public void setClientip(String clientip) {
		this.clientip = clientip;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getLogdescription() {
		return logdescription;
	}

	public void setLogdescription(String logdescription) {
		this.logdescription = logdescription;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
}
