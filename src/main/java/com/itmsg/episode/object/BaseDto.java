package com.itmsg.episode.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public abstract class BaseDto {

    private String userId;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    @Override
    public final String toString () {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected boolean equalsString (final String value1, final String value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 != null && value2 != null) {
            if (value1 == value2) {
                return true;
            } else if (value1.length() != value2.length()) {
                return false;
            } else {
                return value1.equals(value2);
            }
        } else {
            return false;
        }
    }

    protected boolean equalsLong (final Long value1, final Long value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 != null && value2 != null) {
            if (value1 == value2) {
                return true;
            } else if (value1.longValue() == value2.longValue()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected boolean equalsDate (final LocalDateTime value1, final LocalDateTime value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 != null && value2 != null) {
            if (value1 == value2) {
                return true;
            } else if (value1.isEqual(value2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
