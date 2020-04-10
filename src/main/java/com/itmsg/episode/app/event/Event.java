/**
* 이벤트 엔터티 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Event.TABLE_NAME)
public class Event implements Serializable {

    public static final String TABLE_NAME="EVENT";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_NAME+"_SEQ")
    @SequenceGenerator(name = TABLE_NAME+"_SEQ", sequenceName = TABLE_NAME+"_SEQ", allocationSize = 1)
    private Long id;

	@Column(name = "CI_ID", length = 30 )
	private String ciId;

	@Column(name = "EVTRESOURCE", length = 16 )
	private String evtresource;

	@Column(name = "STATUS", length = 8 )
	private String status;

	@Column(name = "SEVERITY", length = 16 )
	private String severity;

	@Column(name = "LEVEL" )
	private Long level;

	@Column(name = "PRELEVEL" )
	private Long prelevel;

	@Column(name = "DEVICENAME" )
	private String devicename;

	@Column(name = "DEVICEIP" )
	private String deviceip;

	@Column(name = "APPNAME" )
	private String appname;

	@Column(name = "KEYNAME" )
	private String keyname;

	@Column(name = "MESSAGE", length = 255 )
	private String message;

	@Column(name = "EVTCONDITION", length = 255 )
	private String evtcondition;

	@Column(name = "SUPPCOUNT" )
	private Long suppcount;

	@Column(name = "ACKBY")
	private String ackby;

	@Column(name = "ACK_DT")
	private Date ackDt;

	@Column(name = "CLOSEBY")
	private String closeby;

	@Column(name = "CLOSE_DT")
	private Date closeDt;

	@Column(name = "DATASRCKEYINT")
	private Long datasrckeyint;

	@Column(name = "DATASRCKEYCHAR")
	private String datasrckeychar;

	@Column(name = "DATASRC")
	private String datasrc;
	
    @Column(name = "ORIGIN_DT")
	private Date originDt;

	@Column(name = "SUPP_DT")
	private Date suppDt;

	@Column(name = "CREATE_DT")
	private Date createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Date updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;
	
	@Transient   
	private String idCi;
	
	@Transient
	@Column(name = "EXTALN17")   
    private String extaln17;

	@Transient
	@Column(name = "CLASSSTRUCTURE_ID")
	private String classstructureId;

	@Transient
	@Column(name = "CINAME")
	private String ciname;

	@Transient
	@Column(name = "ANCESTOR")
	private String ancestor;
	
	@Transient
	@Column(name = "CLASSSTRUCTURE_NM")
	private String classstructureNm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getEvtresource() {
		return evtresource;
	}

	public void setEvtresource(String evtresource) {
		this.evtresource = evtresource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getPrelevel() {
		return prelevel;
	}

	public void setPrelevel(Long prelevel) {
		this.prelevel = prelevel;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEvtcondition() {
		return evtcondition;
	}

	public void setEvtcondition(String evtcondition) {
		this.evtcondition = evtcondition;
	}

	public Long getSuppcount() {
		return suppcount;
	}

	public void setSuppcount(Long suppcount) {
		this.suppcount = suppcount;
	}

	public String getAckby() {
		return ackby;
	}

	public void setAckby(String ackby) {
		this.ackby = ackby;
	}

	public Date getAckDt() {
		return ackDt;
	}

	public void setAckDt(Date ackDt) {
		this.ackDt = ackDt;
	}

	public String getCloseby() {
		return closeby;
	}

	public void setCloseby(String closeby) {
		this.closeby = closeby;
	}

	public Date getCloseDt() {
		return closeDt;
	}

	public void setCloseDt(Date closeDt) {
		this.closeDt = closeDt;
	}

	public Long getDatasrckeyint() {
		return datasrckeyint;
	}

	public void setDatasrckeyint(Long datasrckeyint) {
		this.datasrckeyint = datasrckeyint;
	}

	public String getDatasrckeychar() {
		return datasrckeychar;
	}

	public void setDatasrckeychar(String datasrckeychar) {
		this.datasrckeychar = datasrckeychar;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public Date getOriginDt() {
		return originDt;
	}

	public void setOriginDt(Date originDt) {
		this.originDt = originDt;
	}

	public Date getSuppDt() {
		return suppDt;
	}

	public void setSuppDt(Date suppDt) {
		this.suppDt = suppDt;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getIdCi() {
		return idCi;
	}

	public void setIdCi(String idCi) {
		this.idCi = idCi;
	}

	public String getextaln17() {
		return extaln17;
	}

	public void setextaln17(String extaln17) {
		this.extaln17 = extaln17;
	}

	public String getClassstructureId() {
		return classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getAncestor() {
		return ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
	}

	public String getClassstructureNm() {
		return classstructureNm;
	}

	public void setClassstructureNm(String classstructureNm) {
		this.classstructureNm = classstructureNm;
	}


}