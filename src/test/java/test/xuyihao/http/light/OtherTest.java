package test.xuyihao.http.light;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

import xuyihao.http.light.ConnectorFactory;
import xuyihao.http.light.entity.Cookie;

public class OtherTest {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static String input() {
		String message = "";
		try {
			message = reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public static void output(String message) {
		System.out.println(message);
	}

	@Test
	public void test() {
		String result = ConnectorFactory.getHttpRequestSender()
				.executeGet("http://115.28.192.61:9680/monitor/rest/v1/client/network/available");
		output(result);
		assertEquals(true, result.contains("true"));

		result = ConnectorFactory.getHttpRequestSender().executePostByJSON(
				"http://115.28.192.61:9680/monitor/rest/v1/client/user/bind",
				"{\"name\":\"Johnson\", \"password\":\"12345678\"}");
		output(result);

		String tocken = input();
		Cookie cookie = new Cookie();
		cookie.addCookieValue("tocken", tocken);

		String networkId = input();
		result = ConnectorFactory.getHttpRequestSender(cookie)
				.executeGet("http://115.28.192.61:9680/monitor/rest/v1/client/network/" + networkId + "/add");
		output(result);

	}

}
