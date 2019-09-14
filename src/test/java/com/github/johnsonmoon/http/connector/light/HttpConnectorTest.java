package com.github.johnsonmoon.http.connector.light;

import com.github.johnsonmoon.http.connector.light.entity.Header;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Create by xuyh at 2019/9/14 16:11.
 */
@SuppressWarnings("all")
public class HttpConnectorTest {
    private static Logger logger = LoggerFactory.getLogger(HttpConnectorTest.class);
    private static String BASE_URL = "http://127.0.0.1:8080/test/";
    private static String BASE_RECEIVE_FILE_DIR = "/opt/conn-receive/";
    private static String BASE_SEND_FILE = "/opt/test.pdf";

    @Test
    public void getTest1() {
        System.out.println(ConnectorFactory.getHttpConnector().get(BASE_URL + "1", Header.emptyHeader()));
    }

    @Test
    public void getTest2() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "get-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().get(BASE_URL + "2", Header.emptyHeader(), outputStream, true));
    }

    @Test
    public void deleteTest1() {
        System.out.println(ConnectorFactory.getHttpConnector().delete(BASE_URL + "1", Header.emptyHeader()));
    }

    @Test
    public void deleteTest2() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "delete-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().delete(BASE_URL + "2", Header.emptyHeader(), outputStream, true));
    }

    @Test
    public void postTest1() {
        System.out.println(ConnectorFactory.getHttpConnector().post(BASE_URL + "1", Header.emptyHeader(), "Hello!"));
    }

    @Test
    public void postTest2() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(BASE_SEND_FILE));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().post(BASE_URL + "2", Header.emptyHeader(), inputStream));
    }

    @Test
    public void postTest3() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "post-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().post(BASE_URL + "3", Header.emptyHeader(), "Hello!", outputStream, true));
    }

    @Test
    public void postTest4() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(BASE_SEND_FILE));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "post-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().post(BASE_URL + "4", Header.emptyHeader(), inputStream, outputStream, true));
    }

    @Test
    public void putTest1() {
        System.out.println(ConnectorFactory.getHttpConnector().put(BASE_URL + "1", Header.emptyHeader(), "Hello!"));
    }

    @Test
    public void putTest2() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(BASE_SEND_FILE));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().put(BASE_URL + "2", Header.emptyHeader(), inputStream));
    }

    @Test
    public void putTest3() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "put-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().put(BASE_URL + "3", Header.emptyHeader(), "Hello!", outputStream, true));
    }

    @Test
    public void putTest4() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(BASE_SEND_FILE));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile(BASE_RECEIVE_FILE_DIR + "put-" + System.currentTimeMillis() + ".pdf"));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        System.out.println(ConnectorFactory.getHttpConnector().put(BASE_URL + "4", Header.emptyHeader(), inputStream, outputStream, true));
    }

    public static File newFile(String filePathName) {
        File file = new File(filePathName);
        if (!file.exists()) {
            if (!makeDir(file.getParentFile())) {
                return null;
            }
            try {
                if (!file.createNewFile()) {
                    return null;
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        return file;
    }

    private static boolean makeDir(File dir) {
        if (!dir.exists()) {
            File parent = dir.getParentFile();
            if (parent != null) {
                makeDir(parent);
            }
            return dir.mkdir();
        }
        return true;
    }
}
