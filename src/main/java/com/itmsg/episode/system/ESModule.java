package com.itmsg.episode.system;

public enum ESModule {
    SYSTEM("SYSTEM 공통 적용"),
    ASSET("ASSET FORCE"),
    SERVICE("SERVICE FORCE"),
    MONITOR("MONITOR FORCE");
    
    private String description;

    ESModule(String description) {
        this.description = description;
    }

    public String description(){
        return description;
    }
}