package com.github.johnsonmoon.http.connector.light;

import com.github.johnsonmoon.http.connector.light.connector.HttpConnector;
import com.github.johnsonmoon.http.connector.light.entity.Cookie;

/**
 * Connector factory type.
 * <p>
 * Created by Xuyh at 2017年3月27日 下午4:39:10.
 */
public class ConnectorFactory {
    /**
     * Singleton object
     */
    private static HttpConnector httpConnector;

    /**
     * Get HttpConnector object.
     *
     * @return {@link HttpConnector}
     */
    public static HttpConnector getHttpConnector() {
        if (httpConnector == null)
            httpConnector = new HttpConnector();
        return httpConnector;
    }

    /**
     * Get HttpConnector with old cookie {@link Cookie}
     *
     * @param cookie {@link Cookie}
     * @return {@link HttpConnector}
     */
    public static HttpConnector getHttpRequestSender(Cookie cookie) {
        if (httpConnector == null)
            httpConnector = new HttpConnector();
        httpConnector.setCookie(cookie);
        return httpConnector;
    }
}
