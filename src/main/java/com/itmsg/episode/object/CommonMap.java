package com.itmsg.episode.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonMap extends HashMap<String, Object> {

    public boolean getBoolean(String key) {

        final Object value = super.get(key);
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        switch (value.toString().toLowerCase()) {
            case "y":
            case "1":
            case "yes":
                return true;
            case "n":
            case "0":
            case "no":
                return false;
        }
        try {
            return Boolean.parseBoolean(value.toString());
        } catch (final IllegalArgumentException ignored) {
            return false;
        }
    }

    public String getString(String key) {

        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {

        final Object value = super.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    public int getInt(String key) {

        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {

        final Object value = get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt(value.toString());
            } catch (final IllegalArgumentException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public double getDouble(String key) {

        return getDouble(key, 0D);
    }

    public double getDouble(String key, double defaultValue) {

        final Object value = get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble(value.toString());
            } catch (final IllegalArgumentException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public List<String> getList(String key) {
        final Object value = super.get(key);
        if (value == null) {
            return new ArrayList<>();
        } else if (value instanceof List) {
            final List<Object> temp = (List<Object>) value;
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < temp.size(); i++) {
                list.add(temp.get(i).toString());
            }
            return list;
        } else if (value.getClass().isArray()) {
            final Object[] temp = (Object[]) value;
            final List<String> list = new ArrayList<>();
            for (final Object element : temp) {
                list.add(element.toString());
            }
            return list;
        } else {
            final List<String> list = new ArrayList<>();
            list.add(value.toString());
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public String[] getArray(String key) {

        try {
            final Object value = super.get(key);
            if (value == null) {
                return new String[] {};
            } else if (value.getClass().isArray()) {
                if (value instanceof String[]) {
                    return (String[]) value;
                } else {
                    final Object[] temp = (Object[]) value;
                    final List<String> list = new ArrayList<>();
                    for (final Object element : temp) {
                        list.add(element.toString());
                    }
                    return list.toArray(new String[] {});
                }
            } else if (value instanceof List) {
                final List<Object> temp = (List<Object>) value;
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < temp.size(); i++) {
                    list.add(temp.get(i).toString());
                }
                return list.toArray(new String[] {});
            } else {
                return new String[] { value.toString() };
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {

        try {
            return (T) get(key);
        } catch (final Exception e) {
            return null;
        }
    }

    public boolean checkKeys(String... keys) {

        for (final String key : keys) {
            if (!super.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValues(String... keys) {

        for (final String key : keys) {
            if (get(key) == null || getString(key).equals("") || getArray(key).length == 0) {
                return false;
            }
        }
        return true;
    }

    public Object renameKey(String oldName, String newName) {
        if (!containsKey(oldName)) {
            return null;
        }
        if (containsKey(newName)) {
            return get(newName);
        }
        final Object value = super.put(newName, get(oldName));
        remove(oldName);
        return value;
    }

    @Override
    public Object put(String key, Object value) {

        if (value instanceof Clob) {
            final String strValue = clob2String((Clob) value);
            return super.put(convertCamelCase(key), strValue);
        } else {
            return super.put(convertCamelCase(key), value);
        }
    }

    public Object putDefault(String key, Object value) {
        if (this.getString(key).equals("")) {
            return this.put(key, value);
        } else {
            return this.getString(key);
        }
    }

    public void putArray(String[] keys, Object[] values) {

        for (int i = 0; i < keys.length && i < values.length; i++) {
            super.put(keys[i], values[i]);
        }
    }

    private String convertCamelCase(String key) {

        if (StringUtils.isEmpty(StringUtils.trimWhitespace(key))) {
            return "";
        }
        if (key.indexOf("_") == -1 && Character.isLowerCase(key.charAt(0))) {
            return key;
        }
        final StringBuilder sb = new StringBuilder();

        final char[] array = key.toLowerCase().toCharArray();
        boolean isUnderScore = false;
        for (final char element : array) {
            if (element == '_') {
                isUnderScore = true;
            } else {
                if (isUnderScore) {
                    sb.append(Character.toUpperCase(element));
                    isUnderScore = false;
                } else {
                    sb.append(element);
                }
            }
        }
        return sb.toString();
    }

    private String clob2String(Clob data) {
        if (data == null) {
            return "";
        }
        try {
            final StringBuffer strOut = new StringBuffer();
            String str = "";
            final BufferedReader br = new BufferedReader(data.getCharacterStream());
            while ((str = br.readLine()) != null) {
                strOut.append(str);
            }
            return strOut.toString();
        } catch (final SQLException e) {
            return "";
        } catch (final IOException e) {
            return "";
        }
    }

    public void transArray(String... keys) {
        for (String key : keys) {
            this.put(key, this.getArray(key));
        }
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
