package cn.yangtengfei.api.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtils {

	public static HttpURLConnection initHttpURLConnection(String url,String method,String cookie) throws IOException {
		System.out.println("initHttpURLConnection url is: " + url);
		URL notebooksUrl = new URL(url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) notebooksUrl.openConnection();
		setHeader(httpURLConnection,method,cookie);
		return httpURLConnection;
	}

	public static void setHeader(HttpURLConnection httpURLConnection, String method, String cookies) throws ProtocolException {
		setCommonHeader(httpURLConnection);
		httpURLConnection.setRequestMethod(method);
		httpURLConnection.setRequestProperty("cookie", cookies);
	}

	public static void setCommonHeader(HttpURLConnection httpURLConnection) {
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestProperty("Accept", "application/json");
		httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		httpURLConnection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
		httpURLConnection.setRequestProperty("cache-control", "max-age=0");
		//httpURLConnection.setRequestProperty("cookie", "__yadk_uid=0eBMpm68biDOXeQSKpdb9Sbo3SCXYdNS; read_mode=day; default_font=font2; locale=zh-CN; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575113649,1575176311,1575177653,1575177668; remember_user_token=W1sxNjY1MjYwOV0sIiQyYSQxMSRFdk9MdFRDZndoc3JFNG1DUFdhSGF1IiwiMTU3NTE3ODYyMC4zNjc4MTE3Il0%3D--d64c1d3ac09932c667900130bf91663435a3563d; _m7e_session_core=b5db6a1f101f8e9a188eb63a9b70543e; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216dae5f7ef6458-0db7836b1c329a-3b65410e-1049088-16dae5f7ef75b0%22%2C%22%24device_id%22%3A%2216dae5f7ef6458-0db7836b1c329a-3b65410e-1049088-16dae5f7ef75b0%22%2C%22props%22%3A%7B%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.baidu.com%2Flink%22%2C%22%24latest_traffic_source_type%22%3A%22%E8%87%AA%E7%84%B6%E6%90%9C%E7%B4%A2%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer_host%22%3A%22www.baidu.com%22%7D%2C%22first_id%22%3A%22%22%7D; Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575182722");
		httpURLConnection.setRequestProperty("if-none-match", "W/\"9bb01d3ee52e224430c038c0353df76e\"");
		httpURLConnection.setRequestProperty("upgrade-insecure-requests", "1");
		httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
	}


}
