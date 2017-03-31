package xuyihao.http.light.connector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import xuyihao.http.light.entity.Cookie;

/**
 * 网络请求类
 *
 * <pre>
 * 发送GET POST请求, 接收字符串返回值
 * 添加会话(session)支持[cookie实现]
 * </pre>
 *
 * Created by Xuyh on 2016/12/9.
 */
public class HttpRequestSender {

	/**
	 * cookie的配置逻辑： 每次请求发送时候都会在请求头带上cookie信息(如果cookie为null则不带上),
	 * 然后从响应头中获取新的cookie值刷新当前值,可以起到保存同服务器的会话的作用
	 */
	private Cookie cookie = null;

	public static HttpRequestSender getInstance() {
		return new HttpRequestSender();
	}

	public static HttpRequestSender getInstance(Cookie cookie) {
		return new HttpRequestSender(cookie);
	}

	private HttpRequestSender() {
	}

	private HttpRequestSender(Cookie cookie) {
		this.cookie = cookie;
	}

	public Cookie getCookie() {
		return cookie;
	}

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	/**
	 * 删除cookie信息，使cookie无效
	 */
	public void invalidateCookie() {
		this.cookie = null;
	}

	/**
	 * 执行post发送josn格式body的请求
	 * 
	 * <pre>
	 * 发送请求的请求body由json字串组成
	 * </pre>
	 * 
	 * @param actionURL 发送post请求的URL地址
	 * @param requestBody 请求body
	 * @return
	 */
	public String executePostByJSON(String actionURL, String requestBody) {
		String response = "";
		try {
			URL url = new URL(actionURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "application/json");
			// 如果cookie不为空
			if (this.cookie != null) {
				connection.setRequestProperty("Cookie", this.cookie.convertCookieToCookieValueString());
			}
			// 设置请求数据内容
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			// 使用write(requestContent.getBytes())是为了防止中文出现乱码
			ds.write(requestBody.getBytes());
			ds.flush();
			// 获取服务器响应头的cookie信息
			String set_cookie = connection.getHeaderField("Set-Cookie");
			if (set_cookie != null && !set_cookie.equals("")) {
				this.cookie = Cookie.newCookieInstance(set_cookie);
			}
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				response = "";
			}
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

	/**
	 * 执行发送post请求的方法
	 *
	 * <pre>
	 * 发送请求使用enctype="text/plain"编码方式
	 * 参数形式形如key1=value1&key2=value2
	 * 如果存在会话，本方法可以保持会话，如果要消除会话，请使用invalidateCookie方法
	 * </pre>
	 *
	 * @param actionURL 发送post请求的URL地址
	 * @param parameters 发送post请求数据段中的参数,以Map<String, String>形式传入key=value值
	 * @return "" if no response get
	 */
	public String executePostByUsual(String actionURL, Map<String, String> parameters) {
		String response = "";
		try {
			URL url = new URL(actionURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "text/plain");
			// 如果cookie不为空
			if (this.cookie != null) {
				connection.setRequestProperty("Cookie", this.cookie.convertCookieToCookieValueString());
			}
			// 设置请求数据内容
			String requestContent = "";
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				requestContent = requestContent + key + "=" + parameters.get(key) + "&";
			}
			requestContent = requestContent.substring(0, requestContent.lastIndexOf("&"));
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			// 使用write(requestContent.getBytes())是为了防止中文出现乱码
			ds.write(requestContent.getBytes());
			ds.flush();
			// 获取服务器响应头的cookie信息
			String set_cookie = connection.getHeaderField("Set-Cookie");
			if (set_cookie != null && !set_cookie.equals("")) {
				this.cookie = Cookie.newCookieInstance(set_cookie);
			}
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				response = "";
			}
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

	/**
	 * 执行发送post请求的方法
	 *
	 * <pre>
	 * 发送请求使用enctype="application/x-www-form-urlencoded"编码方式
	 * 参数形式形如key1=value1&key2=value2
	 * 如果存在会话，本方法可以保持会话，如果要消除会话，请使用invalidateCookie方法
	 * </pre>
	 *
	 * @param actionURL 发送post请求的URL地址
	 * @param parameters 发送post请求数据段中的参数,以Map<String, String>形式传入key=value值
	 * @return "" if no response get
	 */
	public String executePostByUrlencoded(String actionURL, Map<String, String> parameters) {
		String response = "";
		try {
			URL url = new URL(actionURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 如果cookie不为空
			if (this.cookie != null) {
				connection.setRequestProperty("Cookie", this.cookie.convertCookieToCookieValueString());
			}
			// 设置请求数据内容
			String requestContent = "";
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				requestContent = requestContent + key + "=" + parameters.get(key) + "&";
			}
			requestContent = requestContent.substring(0, requestContent.lastIndexOf("&"));
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			// 使用write(requestContent.getBytes())是为了防止中文出现乱码
			ds.write(requestContent.getBytes());
			ds.flush();
			// 获取服务器响应头的cookie信息
			String set_cookie = connection.getHeaderField("Set-Cookie");
			if (set_cookie != null && !set_cookie.equals("")) {
				this.cookie = Cookie.newCookieInstance(set_cookie);
			}
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				response = "";
			}
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

	/**
	 * 执行发送get请求的方法
	 * <p>
	 *
	 * <pre>
	 * 直接通过actionURL发送请求,用户也可以自己设置actionURL后面的参数
	 * 这个方法比Map传递参数的方法性能要高
	 * 如果存在会话，本方法可以保持会话，如果要消除会话，请使用invalidateCookie方法
	 * </pre>
	 *
	 * @param actionURL 发送get请求的URL地址(例如：http://www.johnson.cc:8080/Test/download)
	 * @return "" if no response get
	 */
	public String executeGet(String actionURL) {
		String response = "";
		try {
			String trueRequestURL = actionURL;
			URL url = new URL(trueRequestURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 如果cookie不为空
			if (this.cookie != null) {
				connection.setRequestProperty("Cookie", this.cookie.convertCookieToCookieValueString());
			}
			// 获取服务器响应头的cookie信息
			String set_cookie = connection.getHeaderField("Set-Cookie");
			if (set_cookie != null && !set_cookie.equals("")) {
				this.cookie = Cookie.newCookieInstance(set_cookie);
			}
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				response = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

	/**
	 * 执行发送get请求的方法
	 * <p>
	 *
	 * <pre>
	 * 最后发送的URL格式为(例如:http://www.johnson.cc:8080/Test/download?file=file1&name=xxx&pwd=aaa)
	 * 如果存在会话，本方法可以保持会话，如果要消除会话，请使用invalidateCookie方法
	 * </pre>
	 *
	 * @param actionURL 发送get请求的URL地址(例如：http://www.johnson.cc:8080/Test/download)
	 * @param parameters 发送get请求URL后跟着的具体参数,以Map<String, String>形式传入key=value值
	 * @return "" if no response get
	 */
	public String executeGet(String actionURL, Map<String, String> parameters) {
		String response = "";
		try {
			String trueRequestURL = actionURL;
			trueRequestURL += "?";
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				trueRequestURL = trueRequestURL + key + "=" + parameters.get(key) + "&";
			}
			trueRequestURL = trueRequestURL.substring(0, trueRequestURL.lastIndexOf("&"));
			URL url = new URL(trueRequestURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 如果cookie不为空
			if (this.cookie != null) {
				connection.setRequestProperty("Cookie", this.cookie.convertCookieToCookieValueString());
			}
			// 获取服务器响应头的cookie信息
			String set_cookie = connection.getHeaderField("Set-Cookie");
			if (set_cookie != null && !set_cookie.equals("")) {
				this.cookie = Cookie.newCookieInstance(set_cookie);
			}
			try {
				// 获取URL的响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String s = "";
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					s += temp;
				}
				response = s;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				response = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Request failed!");
		}
		return response;
	}

}
