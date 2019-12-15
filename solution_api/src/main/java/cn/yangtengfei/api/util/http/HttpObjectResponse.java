package cn.yangtengfei.api.util.http;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpObjectResponse {

	public static <E> E getObject(Class<E> eClass, String url,String method,String cookie) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection(url,method,cookie);
		InputStream inputStream = httpURLConnection.getInputStream();
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = inputStream.read(b)) != -1) {
			stringBuffer.append(new String(b, 0, length, "utf-8"));
		}
		if (inputStream != null) {
			inputStream.close();
		}
		String result = stringBuffer.toString();
		E e = JSON.parseObject(result, eClass);
		httpURLConnection.disconnect();
		return e;
	}
}
