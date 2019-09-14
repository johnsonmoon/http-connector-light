package com.github.johnsonmoon.http.connector.light.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Cookie entity
 */
public class Cookie {
    public enum CookieAttribute {
        COOKIE_ATTRIBUTE_NAME_EXPIRE("expire"),
        COOKIE_ATTRIBUTE_NAME_PATH("path"),
        COOKIE_ATTRIBUTE_NAME_DOMAIN("domain"),
        COOKIE_ATTRIBUTE_NAME_SECURE("secure"),
        COOKIE_ATTRIBUTE_NAME_HTTP_ONLY("httpOnly"),
        COOKIE_ATTRIBUTE_NAME_HTTP_ONLY_LOWERCASE("httponly");
        private String name;

        CookieAttribute(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * cookie values
     */
    private Map<String, String> cookieValue = new HashMap<>();
    private String expire = "";
    private String path = "";
    private String domain = "";
    private boolean secure = false;
    private boolean httpOnly = false;

    /**
     * Create cookie object by 'Set-Cookie' header response string from server.
     *
     * @param setCookie 'Set-Cookie' header value
     * @return {@link Cookie}
     */
    public static Cookie create(String setCookie) {
        Cookie cookie = new Cookie();
        String[] params = setCookie.split("; ");
        for (String param : params) {
            if (param.toLowerCase().contains(CookieAttribute.COOKIE_ATTRIBUTE_NAME_PATH.getName())) {
                cookie.path = param.substring(param.indexOf("=") + 1);
            } else if (param.toLowerCase().contains(CookieAttribute.COOKIE_ATTRIBUTE_NAME_DOMAIN.getName())) {
                cookie.domain = param.substring(param.indexOf("=") + 1);
            } else if (param.toLowerCase().contains(CookieAttribute.COOKIE_ATTRIBUTE_NAME_EXPIRE.getName())) {
                cookie.expire = param.substring(param.indexOf("=") + 1);
            } else if (param.toLowerCase().contains(CookieAttribute.COOKIE_ATTRIBUTE_NAME_SECURE.getName())) {
                cookie.secure = true;
            } else if (param.toLowerCase().contains(CookieAttribute.COOKIE_ATTRIBUTE_NAME_HTTP_ONLY_LOWERCASE.getName())) {
                cookie.httpOnly = true;
            } else {
                String[] nameAndValue = param.split("=");
                cookie.addCookieValue(nameAndValue[0], nameAndValue[1]);
            }
        }
        return cookie;
    }

    /**
     * Build 'Set-Cookie' value for response header.
     *
     * @return 'Set-Cookie' string value
     */
    public String buildSetCookie() {
        String setCookie = "";
        String cookieValueString = "";
        for (String name : this.cookieValue.keySet()) {
            cookieValueString += ("; " + name + "=" + this.cookieValue.get(name));
        }
        cookieValueString = cookieValueString.substring(2);
        setCookie += cookieValueString;
        if (this.path != null && !this.path.equals("")) {
            setCookie += ("; " + CookieAttribute.COOKIE_ATTRIBUTE_NAME_PATH.getName() + "=" + this.path);
        }
        if (this.domain != null && !this.domain.equals("")) {
            setCookie += ("; " + CookieAttribute.COOKIE_ATTRIBUTE_NAME_DOMAIN.getName() + "=" + this.domain);
        }
        if (this.expire != null && !this.expire.equals("")) {
            setCookie += (";" + CookieAttribute.COOKIE_ATTRIBUTE_NAME_EXPIRE.getName() + "=" + this.expire);
        }
        if (this.secure) {
            setCookie += ("; " + CookieAttribute.COOKIE_ATTRIBUTE_NAME_SECURE.getName());
        }
        if (this.httpOnly) {
            setCookie += ("; " + CookieAttribute.COOKIE_ATTRIBUTE_NAME_HTTP_ONLY.getName());
        }
        return setCookie;
    }

    /**
     * Build 'Cookie' value for request header.
     *
     * @return 'Cookie' string value
     */
    public String buildCookie() {
        String cookieValueString = "";
        for (String name : this.cookieValue.keySet()) {
            cookieValueString += ("; " + name + "=" + this.cookieValue.get(name));
        }
        cookieValueString = cookieValueString.substring(2);
        return cookieValueString;
    }

    /**
     * Add cookie value.
     *
     * @param name  name for cookie
     * @param value value for cookie
     */
    public void addCookieValue(String name, String value) {
        cookieValue.put(name, value);
    }

    public void removeCookieValue(String name) {
        cookieValue.remove(name);
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookieValue=" + cookieValue +
                ", expire='" + expire + '\'' +
                ", path='" + path + '\'' +
                ", domain='" + domain + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                '}';
    }
}
