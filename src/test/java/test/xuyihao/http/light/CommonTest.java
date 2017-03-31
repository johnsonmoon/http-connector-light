package test.xuyihao.http.light;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import xuyihao.http.light.ConnectorFactory;
import xuyihao.http.light.connector.HttpRequestSender;

public class CommonTest {
	private HttpRequestSender sender = ConnectorFactory.getHttpRequestSender();

	@Test
	public void testExecutePostByJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "XuJunchao");
		jsonObject.put("password", "12345678");
		jsonObject.put("phoneNum", "15700085085");
		jsonObject.put("email", "12121@qq.com");
		String result = sender.executePostByJSON("http://115.28.192.61:9680/monitor/rest/v1/client/user",
				jsonObject.toString());
		System.out.println(result);
		JSONObject resultObject = new JSONObject(result);
		assertEquals(true, Boolean.parseBoolean(resultObject.get("result").toString()));
	}

	@Test
	public void testExecuteGetString() {
		String result = sender.executeGet("http://115.28.192.61:9680/monitor/rest/v1/client/network/available");
		System.out.println(result);
		assertEquals(true, result.contains("true"));
	}
}
