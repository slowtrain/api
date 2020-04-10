package com.itmsg.episode.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class DejavuProperties {

    @Value("${system.app-name}")
    private String systemAppName;

    @Value("${system.app-version}")
    private String systemAppVersion;

    @Value("${system.app-build}")
    private String systemAppBuild;

    @Value("${system.app-desc}")
    private String systemAppDesc;

    @Value("${system.client-name}")
    private String systemClientName;

    @Value("${system.db-type}")
    private String systemDbType;

    @Value("${log.request}")
    private Boolean logRequest;

    @Value("${log.uri}")
    private Boolean logUri;

    @Value("${log.params}")
    private Boolean logParams;

    @Value("${log.web}")
    private Boolean logController;

    @Value("${log.sql}")
    private Boolean logSql;

    @Value("${log.view}")
    private Boolean logView;

    @Value("${paging.rowPerPage}")
    private Integer rowPerPage;

    @Value("${paging.pagePerBlock}")
    private Integer pagePerBlock;

    @Value("${login.use-log}")
    private Boolean loginUseLog;

    @Value("${login.use-limit}")
    private Boolean loginUseLimit;

    @Value("${login.limit-cnt}")
    private Integer loginLimitCnt;

    @Value("${login.use-multi}")
    private Boolean loginUseMulti;

    @Value("${login.multi-expired}")
    private Boolean loginMulitExpired;

    @Value("${api.facebook.app-id}")
    private String facebookAppId;

    @Value("${api.facebook.app-secret}")
    private String facebookAppSecret;

    @Value("${api.naver.client-id}")
    private String naverClientId;

    @Value("${api.naver.client-secret}")
    private String naverClientSecret;

	public String getSystemAppName() {
		return this.systemAppName;
	}

	public void setSystemAppName(String systemAppName) {
		this.systemAppName = systemAppName;
	}
	
	public String getSystemAppVersion() {
		return this.systemAppVersion;
	}

	public void setSystemAppVersion(String systemAppVersion) {
		this.systemAppVersion = systemAppVersion;
	}

	public String getSystemAppBuild() {
		return this.systemAppBuild;
	}

	public void setSystemAppBuild(String systemAppBuild) {
		this.systemAppBuild = systemAppBuild;
	}

	public String getSystemAppDesc() {
		return this.systemAppDesc;
	}

	public void setSystemAppDesc(String systemAppDesc) {
		this.systemAppDesc = systemAppDesc;
	}

	public String getSystemClientName() {
		return this.systemClientName;
	}

	public void setSystemClientName(String systemClientName) {
		this.systemClientName = systemClientName;
	}

	public String getSystemDbType() {
		return this.systemDbType;
	}

	public void setSystemDbType(String systemDbType) {
		this.systemDbType = systemDbType;
	}

	public Boolean getLogRequest() {
		return this.logRequest;
	}

	public void isLogRequest(Boolean logRequest) {
		this.logRequest = logRequest;
	}

	public Boolean getLogUri() {
		return this.logUri;
	}

	public void isLogUri(Boolean logUri) {
		this.logUri = logUri;
	}

	public Boolean getLogParams() {
		return this.logParams;
	}

	public void isLogParams(Boolean logParams) {
		this.logParams = logParams;
	}

	public Boolean getLogController() {
		return this.logController;
	}

	public void isLogController(Boolean logController) {
		this.logController = logController;
	}

	public Boolean getLogSql() {
		return this.logSql;
	}

	public void isLogSql(Boolean logSql) {
		this.logSql = logSql;
	}

	public Boolean getLogView() {
		return this.logView;
	}

	public void isLogView(Boolean logView) {
		this.logView = logView;
	}

	public Integer getRowPerPage() {
		return this.rowPerPage;
	}

	public void setRowPerPage(Integer rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public Integer getPagePerBlock() {
		return this.pagePerBlock;
	}

	public void setPagePerBlock(Integer pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public Boolean getLoginUseLog() {
		return this.loginUseLog;
	}

	public void isLoginUseLog(Boolean loginUseLog) {
		this.loginUseLog = loginUseLog;
	}

	public Boolean getLoginUseLimit() {
		return this.loginUseLimit;
	}

	public void isLoginUseLimit(Boolean loginUseLimit) {
		this.loginUseLimit = loginUseLimit;
	}

	public Integer getLoginLimitCnt() {
		return this.loginLimitCnt;
	}

	public void setLoginLimitCnt(Integer loginLimitCnt) {
		this.loginLimitCnt = loginLimitCnt;
	}

	public Boolean getLoginUseMulti() {
		return this.loginUseMulti;
	}

	public void isLoginUseMulti(Boolean loginUseMulti) {
		this.loginUseMulti = loginUseMulti;
	}

	public Boolean getLoginMulitExpired() {
		return this.loginMulitExpired;
	}

	public void isLoginMulitExpired(Boolean loginMulitExpired) {
		this.loginMulitExpired = loginMulitExpired;
	}

	public String getFacebookAppId() {
		return this.facebookAppId;
	}

	public void setFacebookAppId(String facebookAppId) {
		this.facebookAppId = facebookAppId;
	}

	public String getFacebookAppSecret() {
		return this.facebookAppSecret;
	}

	public void setFacebookAppSecret(String facebookAppSecret) {
		this.facebookAppSecret = facebookAppSecret;
	}

	public String getNaverClientId() {
		return this.naverClientId;
	}

	public void setNaverClientId(String naverClientId) {
		this.naverClientId = naverClientId;
	}

	public String getNaverClientSecret() {
		return this.naverClientSecret;
	}

	public void setNaverClientSecret(String naverClientSecret) {
		this.naverClientSecret = naverClientSecret;
	}
}