package xuyihao.http.light;

import xuyihao.http.light.connector.HttpRequestSender;
import xuyihao.http.light.entity.Cookie;

/**
 * 网络工具工厂类
 * 
 * <pre>
 * 	单例工具类:
 * 	HttpRequestSender
 * </pre>
 * 
 * Created by Xuyh at 2017年3月27日 下午4:39:10.
 */
public class ConnectorFactory {
	/**
	 * HTTP connectors 单例
	 */
	private static HttpRequestSender httpRequestSender = null;

	/**
	 * 获取HttpRequestSender单例对象
	 * 
	 * @return
	 */
	public static HttpRequestSender getHttpRequestSender() {
		if (httpRequestSender == null)
			httpRequestSender = HttpRequestSender.getInstance();
		return httpRequestSender;
	}

	/**
	 * 获取HttpRequestSender单例对象
	 * 
	 * @param cookie
	 * @return
	 */
	public static HttpRequestSender getHttpRequestSender(Cookie cookie) {
		if (httpRequestSender == null)
			httpRequestSender = HttpRequestSender.getInstance();
		httpRequestSender.setCookie(cookie);
		return httpRequestSender;
	}
}
