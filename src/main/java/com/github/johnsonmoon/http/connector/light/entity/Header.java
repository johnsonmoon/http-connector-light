package com.github.johnsonmoon.http.connector.light.entity;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create by xuyh at 2019/9/14 16:54.
 */
public class Header {
    private Map<String, String> map = new LinkedHashMap<>();

    /**
     * Get an empty header
     *
     * @return {@link Map}
     */
    public static Map<String, String> emptyHeader() {
        return Collections.emptyMap();
    }

    /**
     * Create a header
     *
     * @return {@link Header}
     */
    public static Header builder() {
        return new Header();
    }

    /**
     * Put key-value pair into header
     *
     * @param key   header key
     * @param value header value
     * @return {@link Header}
     */
    public Header put(String key, String value) {
        map.put(key, value);
        return this;
    }

    /**
     * Remove header key-value pair by key
     *
     * @param key header key
     * @return {@link Header}
     */
    public Header remove(String key) {
        map.remove(key);
        return this;
    }

    /**
     * Build header map
     *
     * @return {@link Map}
     */
    public Map<String, String> build() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (map != null) {
            map.forEach((k, v) -> {
                stringBuilder.append(k);
                stringBuilder.append(":");
                stringBuilder.append(v);
                stringBuilder.append(" ");
            });
        }
        return "[" + stringBuilder.substring(0, stringBuilder.length() - 1) + "]";
    }
}
