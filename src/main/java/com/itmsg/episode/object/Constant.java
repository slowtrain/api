package com.itmsg.episode.object;

import com.itmsg.episode.config.property.DejavuProperties;
// import lombok.experimental.UtilityClass;

import javax.annotation.Resource;

// @UtilityClass
public class Constant {

    @Resource
    private static DejavuProperties dejavuProperties;

    public static final String systemAppName = dejavuProperties.getSystemAppName();

    public static final String systemAppVersion = dejavuProperties.getSystemAppVersion();

    public static final String systemAppBuild = dejavuProperties.getSystemAppBuild();

    public static final String systemAppDesc = dejavuProperties.getSystemAppDesc();

    public static final String systemClientName = dejavuProperties.getSystemClientName();

    public static final String systemDbType = dejavuProperties.getSystemDbType();

    public static final Boolean logRequest = dejavuProperties.getLogRequest();

    public static final Boolean logUri = dejavuProperties.getLogUri();

    public static final Boolean logParams = dejavuProperties.getLogParams();

    public static final Boolean logController = dejavuProperties.getLogController();

    public static final Boolean logSql = dejavuProperties.getLogSql();

    public static final Boolean logView = dejavuProperties.getLogView();

    public static final Integer rowPerPage = dejavuProperties.getRowPerPage();

    public static final Integer pagePerBlock = dejavuProperties.getPagePerBlock();

    public static final Boolean loginUseLog = dejavuProperties.getLoginUseLog();

    public static final Boolean loginUseLimit = dejavuProperties.getLoginUseLimit();

    public static final Integer loginLimitCnt = dejavuProperties.getLoginLimitCnt();

    public static final Boolean loginUseMulti = dejavuProperties.getLoginUseMulti();

    public static final Boolean loginMulitExpired = dejavuProperties.getLoginMulitExpired();

    public static final String facebookAppId = dejavuProperties.getFacebookAppId();

    public static final String facebookAppSecret = dejavuProperties.getFacebookAppSecret();

    public static final String naverClientId = dejavuProperties.getNaverClientId();

    public static final String naverClientSecret = dejavuProperties.getNaverClientSecret();

}
