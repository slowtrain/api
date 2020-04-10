package com.itmsg.episode.common;

public enum ContentType {

    JSON("application/json; charset=UTF-8"),
    XML("application/xml; charset=UTF-8");

    private String contentString;

    ContentType(String contentString) {
        this.contentString = contentString;
    }

    public String getContentString() {
        return this.contentString;
    }
}
