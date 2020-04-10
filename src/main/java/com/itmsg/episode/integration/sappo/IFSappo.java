package com.itmsg.episode.integration.sappo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = IFSappo.TABLE_NAME)
public class IFSappo implements Serializable {

	private static final long serialVersionUID = 2302894476361333252L;

	public static final String TABLE_NAME = "IF_SAPPO";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IFSappo.TABLE_NAME + "_SEQ")
	@SequenceGenerator(name = IFSappo.TABLE_NAME + "_SEQ", sequenceName = IFSappo.TABLE_NAME+ "_SEQ", allocationSize = 1)
    private Long id;
	
	@Column(name = "SOURCESYSTEM")
    @NotNull
	private String sourceSystem;

	@Column(name = "TARGETSYSTEM")
    @NotNull
	private String targetSystem;

	@Column(name = "RESULT")
	private String result;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "IC_SEND_OBJECT_ID")
	private String icSendObjectId;
	
	@Column(name = "IC_SEND_PARTY")
	private String icSendParty;

	@Column(name = "IC_SEND_COMPNM")
	private String icSendCompnm;

	@Column(name = "IC_SEND_IFNM")
	private String icSendIfnm;

	@Column(name = "IC_SEND_IFNS")
	private String icSendIfns;

	@Column(name = "IC_SEND_CHANNEL")
	private String icSendChannel;

	@Column(name = "IC_SEND_ADAPTERTYPE")
	private String icSendAdaptertype;

	@Column(name = "IC_SEND_DIRECTION")
	private String icSendDirection;

	@Column(name = "IC_SEND_MSGPROT")
	private String icSendMsgprot;

	@Column(name = "IC_SEND_TRNSPROT")
	private String icSendTrnsprot;

	@Column(name = "IC_RECV_OBJECT_ID")
	private String icRecvObjectId;

	@Column(name = "IC_RECV_PARTY")
	private String icRecvParty;

	@Column(name = "IC_RECV_COMPNM")
	private String icRecvCompnm;

	@Column(name = "IC_RECV_IFNM")
	private String icRecvIfnm;

	@Column(name = "IC_RECV_IFNS")
	private String icRecvIfns;

	@Column(name = "IC_RECV_CHANNEL")
	private String icRecvChannel;

	@Column(name = "IC_RECV_ADAPTERTYPE")
	private String icRecvAdaptertype;

	@Column(name = "IC_RECV_DIRECTION")
	private String icRecvDirection;

	@Column(name = "IC_RECV_MSGPROT")
	private String icRecvMsgprot;

	@Column(name = "IC_RECV_TRNSPROT")
	private String icRecvTrnsprot;

	@Column(name = "CREATE_ID")
    private String createId;

	@Column(name = "CREATE_DT")
    @CreationTimestamp
    private Date createDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIcSendObjectId() {
		return icSendObjectId;
	}

	public void setIcSendObjectId(String icSendObjectId) {
		this.icSendObjectId = icSendObjectId;
	}

	public String getIcSendParty() {
		return icSendParty;
	}

	public void setIcSendParty(String icSendParty) {
		this.icSendParty = icSendParty;
	}

	public String getIcSendCompnm() {
		return icSendCompnm;
	}

	public void setIcSendCompnm(String icSendCompnm) {
		this.icSendCompnm = icSendCompnm;
	}

	public String getIcSendIfnm() {
		return icSendIfnm;
	}

	public void setIcSendIfnm(String icSendIfnm) {
		this.icSendIfnm = icSendIfnm;
	}

	public String getIcSendIfns() {
		return icSendIfns;
	}

	public void setIcSendIfns(String icSendIfns) {
		this.icSendIfns = icSendIfns;
	}

	public String getIcSendChannel() {
		return icSendChannel;
	}

	public void setIcSendChannel(String icSendChannel) {
		this.icSendChannel = icSendChannel;
	}

	public String getIcSendAdaptertype() {
		return icSendAdaptertype;
	}

	public void setIcSendAdaptertype(String icSendAdaptertype) {
		this.icSendAdaptertype = icSendAdaptertype;
	}

	public String getIcSendDirection() {
		return icSendDirection;
	}

	public void setIcSendDirection(String icSendDirection) {
		this.icSendDirection = icSendDirection;
	}

	public String getIcSendMsgprot() {
		return icSendMsgprot;
	}

	public void setIcSendMsgprot(String icSendMsgprot) {
		this.icSendMsgprot = icSendMsgprot;
	}

	public String getIcSendTrnsprot() {
		return icSendTrnsprot;
	}

	public void setIcSendTrnsprot(String icSendTrnsprot) {
		this.icSendTrnsprot = icSendTrnsprot;
	}

	public String getIcRecvObjectId() {
		return icRecvObjectId;
	}

	public void setIcRecvObjectId(String icRecvObjectId) {
		this.icRecvObjectId = icRecvObjectId;
	}

	public String getIcRecvParty() {
		return icRecvParty;
	}

	public void setIcRecvParty(String icRecvParty) {
		this.icRecvParty = icRecvParty;
	}

	public String getIcRecvCompnm() {
		return icRecvCompnm;
	}

	public void setIcRecvCompnm(String icRecvCompnm) {
		this.icRecvCompnm = icRecvCompnm;
	}

	public String getIcRecvIfnm() {
		return icRecvIfnm;
	}

	public void setIcRecvIfnm(String icRecvIfnm) {
		this.icRecvIfnm = icRecvIfnm;
	}

	public String getIcRecvIfns() {
		return icRecvIfns;
	}

	public void setIcRecvIfns(String icRecvIfns) {
		this.icRecvIfns = icRecvIfns;
	}

	public String getIcRecvChannel() {
		return icRecvChannel;
	}

	public void setIcRecvChannel(String icRecvChannel) {
		this.icRecvChannel = icRecvChannel;
	}

	public String getIcRecvAdaptertype() {
		return icRecvAdaptertype;
	}

	public void setIcRecvAdaptertype(String icRecvAdaptertype) {
		this.icRecvAdaptertype = icRecvAdaptertype;
	}

	public String getIcRecvDirection() {
		return icRecvDirection;
	}

	public void setIcRecvDirection(String icRecvDirection) {
		this.icRecvDirection = icRecvDirection;
	}

	public String getIcRecvMsgprot() {
		return icRecvMsgprot;
	}

	public void setIcRecvMsgprot(String icRecvMsgprot) {
		this.icRecvMsgprot = icRecvMsgprot;
	}

	public String getIcRecvTrnsprot() {
		return icRecvTrnsprot;
	}

	public void setIcRecvTrnsprot(String icRecvTrnsprot) {
		this.icRecvTrnsprot = icRecvTrnsprot;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	

	
     

 


}
