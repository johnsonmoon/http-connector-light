package com.github.johnsonmoon.http.connector.light.connector;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.github.johnsonmoon.http.connector.light.entity.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http request connector
 * <p>
 * Created by xuyh on 2016/12/9.
 */
public class HttpConnector {
    private static Logger logger = LoggerFactory.getLogger(HttpConnector.class);

    private Cookie cookie = null;

    /**
     * Get cookie of current connector.
     *
     * @return {@link Cookie}
     */
    public Cookie getCookie() {
        return cookie;
    }

    /**
     * Set cookie for current connector.
     *
     * @param cookie {@link Cookie}
     */
    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    /**
     * Make cookie invalid of current connector.
     */
    public void invalidateCookie() {
        this.cookie = null;
    }

    /**
     * Send 'GET' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @return Response as {@link String}
     */
    public String get(String url, Map<String, String> headers) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("GET");
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * Send 'GET' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long get(String url, Map<String, String> headers, OutputStream responseTo, boolean closeResponseTo) {
        if (responseTo == null) {
            return 0;
        }
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("GET");
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Send 'DELETE' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @return Response as {@link String}
     */
    public String delete(String url, Map<String, String> headers) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("DELETE");
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * Send 'DELETE' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long delete(String url, Map<String, String> headers, OutputStream responseTo, boolean closeResponseTo) {
        if (responseTo == null) {
            return 0;
        }
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("DELETE");
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Send 'PUT' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body    A {@link String} as request body
     * @return Response as {@link String}
     */
    public String put(String url, Map<String, String> headers, String body) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeStrToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Send 'PUT' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body    An {@link InputStream} as request body
     * @return Response as {@link String}
     */
    public String put(String url, Map<String, String> headers, InputStream body) {
        if (body == null) {
            return "";
        }
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeInputStreamToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Send 'PUT' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body            request body {@link String}
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long put(String url, Map<String, String> headers, String body, OutputStream responseTo, boolean closeResponseTo) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeStrToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Send 'PUT' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body            An {@link InputStream} as request body
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long put(String url, Map<String, String> headers, InputStream body, OutputStream responseTo, boolean closeResponseTo) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeInputStreamToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Send 'POST' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body    A {@link String} as request body
     * @return Response as {@link String}
     */
    public String post(String url, Map<String, String> headers, String body) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeStrToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Send 'POST' request
     *
     * @param url     request url
     * @param headers request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body    An {@link InputStream} as request body
     * @return Response as {@link String}
     */
    public String post(String url, Map<String, String> headers, InputStream body) {
        if (body == null) {
            return "";
        }
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeInputStreamToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToStr(inputStream);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Send 'POST' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body            A {@link String} as request body
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long post(String url, Map<String, String> headers, String body, OutputStream responseTo, boolean closeResponseTo) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeStrToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Send 'POST' request
     *
     * @param url             request url
     * @param headers         request headers. Could be built by {@link com.github.johnsonmoon.http.connector.light.entity.Header}
     * @param body            An {@link InputStream} as request body
     * @param responseTo      An {@link OutputStream} which response stream write into
     * @param closeResponseTo An {@link OutputStream} which response stream write into should be closed by current connector. {@code true} means yes.
     * @return response stream length as {@code byte}
     */
    public long post(String url, Map<String, String> headers, InputStream body, OutputStream responseTo, boolean closeResponseTo) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(connection::setRequestProperty);
            }
            cookieWrite(connection);
            writeInputStreamToRequest(connection, body);
            InputStream inputStream = connection.getInputStream();
            cookieRead(connection);
            return readResponseToOutputStream(inputStream, responseTo, closeResponseTo);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0;
        }
    }

    private void writeStrToRequest(HttpURLConnection connection, String body) {
        DataOutputStream bodyOutputStream = null;
        try {
            bodyOutputStream = new DataOutputStream(connection.getOutputStream());
            bodyOutputStream.write(body.getBytes());
            bodyOutputStream.flush();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if (bodyOutputStream != null) {
                try {
                    bodyOutputStream.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }

    private void writeInputStreamToRequest(HttpURLConnection connection, InputStream body) {
        DataOutputStream bodyOutputStream = null;
        try {
            bodyOutputStream = new DataOutputStream(connection.getOutputStream());
            byte[] bodyBuffer = new byte[32];
            int bodyLength;
            while ((bodyLength = body.read(bodyBuffer)) > 0) {
                bodyOutputStream.write(bodyBuffer, 0, bodyLength);
            }
            bodyOutputStream.flush();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if (bodyOutputStream != null) {
                try {
                    bodyOutputStream.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            if (body != null) {
                try {
                    body.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }

    private String readResponseToStr(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream);
            char[] buffer = new char[32];
            int length;
            while ((length = reader.read(buffer)) > 0) {
                stringBuilder.append(buffer, 0, length);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
        return stringBuilder.toString();
    }

    private long readResponseToOutputStream(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream) {
        long lengthSum = 0L;
        try {
            byte[] buffer = new byte[32];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
                lengthSum = lengthSum + length;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            if (closeOutputStream) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
        return lengthSum;
    }

    private void cookieWrite(HttpURLConnection connection) {
        if (this.cookie != null) {
            connection.setRequestProperty("Cookie", this.cookie.buildCookie());
        }
    }

    private void cookieRead(HttpURLConnection connection) {
        String setCookie = connection.getHeaderField("Set-Cookie");
        if (setCookie != null && !setCookie.isEmpty()) {
            this.cookie = Cookie.create(setCookie);
        }
    }
}
