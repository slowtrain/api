package com.itmsg.episode.system;

public enum ESDataFilter {
    USER_LIST(ESDataFilter.DATABASE),
    USERGROUP_LIST(ESDataFilter.DATABASE),
    APPLICATION_LIST(ESDataFilter.DATABASE),
    LOCATION_LIST(ESDataFilter.DATABASE),
    ALARM_LIST(ESDataFilter.DATABASE),
    ORGANIZATION_LIST(ESDataFilter.DATABASE),
    ROLE_LIST(ESDataFilter.DATABASE),
    DOMAIN_LIST(ESDataFilter.DATABASE),
    CLASSIFICATION_LIST(ESDataFilter.DATABASE),
    NAVIGATOR_LIST(ESDataFilter.DATABASE),
    CISW_LIST(ESDataFilter.DATABASE),
    CI_LIST(ESDataFilter.DATABASE),
    CIHIS_LIST(ESDataFilter.DATABASE),
    DISCI_LIST(ESDataFilter.DATABASE),
    EXCELCI_LIST(ESDataFilter.DATABASE),
    LICENSE_LIST(ESDataFilter.DATABASE),
    SWCAT_LIST(ESDataFilter.DATABASE),
    LICENSEUSE_LIST(ESDataFilter.DATABASE),
    CONVERSION_LIST((ESDataFilter.DATABASE)),
    SCHEDULER_LIST((ESDataFilter.DATABASE)),
    SERVICE_LIST((ESDataFilter.DATABASE)),
    COMPANY_LIST((ESDataFilter.DATABASE));
    
    public static final String DATABASE = "DATABASE";
    public static final String API = "API";

    private String type;

    ESDataFilter(String type) {
        this.type = type;
    }

    public String type(){
        return type;
    }

}