package cn.yangtengfei.api.server.pictureServer.luguo;

import cn.yangtengfei.api.util.http.HttpUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuGuoDengLu {

	public static void main(String[] args) throws IOException {
	//public static Map<String,String> session() throws IOException {
		HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection("https://imgchr.com/");
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);


		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("Host", "imgchr.com");
		httpURLConnection.setRequestProperty("Connection", "keep-alive");
		httpURLConnection.setRequestProperty("Accept", "application/json");
		httpURLConnection.setRequestProperty("Origin", "https://imgchr.com");
		httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

		httpURLConnection.setRequestProperty("Referer", "https://imgchr.com/i/l36QgS");
		httpURLConnection.setRequestProperty("Cookie", "_ga=GA1.2.490248484.1576587968; PHPSESSID=g8jeplrikv9j5i4akudvam49g6; Hm_lvt_c1a64f283dcc84e35f0a947fcf3b8594=1577449018,1577706763,1577706788,1577795225; _gid=GA1.2.18614959.1577795225; KEEP_LOGIN=eatg0%3A2cf74704e5f4ea6bda3eeac71a0338ebe0d33b2728b82c268b3b07a8622092d45bd22467902f81c970bb61317457349f3f2be12718582988738abf182a3c4c5727e65226795bb6bfe6aa6f3279d02e2a1b7b430db60da05b3c1571f5325ababd7e7ac9417f48697ed66b0d77d3158dc019dbe%3A1577766627; _gat_gtag_UA_114322073_1=1; Hm_lpvt_c1a64f283dcc84e35f0a947fcf3b8594=1577795524");

		StringBuffer stringBuffer = new StringBuffer();
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
		Pattern p = Pattern.compile("PF.obj.config.auth_token = \"(.*?)\\\";");
		Matcher m = p.matcher(result);
		String token = StringUtils.EMPTY;
		while (m.find()) {
			//System.out.println(m.group(1));//m.group(1)不包括这两个字符
			token = m.group(1);
		}
		System.out.println("token：" + token);


		System.out.println(httpURLConnection.getHeaderField("Set-Cookie"));
		//String cookie = ((HttpsURLConnectionImpl) httpURLConnection)
		String sessionId = "";
		String cookieVal = "";
		String key = null;
		//取cookie
		for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("Cookie")) {
				cookieVal = httpURLConnection.getHeaderField(i);
				cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
				sessionId = sessionId + cookieVal + ";";
			}
		}

		System.out.println(cookieVal);


		httpURLConnection = HttpUtils.initHttpURLConnection("https://imgchr.com/login");
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Host", "imgchr.com");
		httpURLConnection.setRequestProperty("Connection", "keep-alive");
		httpURLConnection.setRequestProperty("Accept", "application/json");
		httpURLConnection.setRequestProperty("Origin", "https://imgchr.com");
		httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
		httpURLConnection.setRequestProperty("Referer", "https://imgchr.com/i/l36QgS");
		httpURLConnection.setRequestProperty("Cookie", "_ga=GA1.2.490248484.1576587968; PHPSESSID=g8jeplrikv9j5i4akudvam49g6; Hm_lvt_c1a64f283dcc84e35f0a947fcf3b8594=1577449018,1577706763,1577706788,1577795225; _gid=GA1.2.18614959.1577795225; KEEP_LOGIN=eatg0%3A2cf74704e5f4ea6bda3eeac71a0338ebe0d33b2728b82c268b3b07a8622092d45bd22467902f81c970bb61317457349f3f2be12718582988738abf182a3c4c5727e65226795bb6bfe6aa6f3279d02e2a1b7b430db60da05b3c1571f5325ababd7e7ac9417f48697ed66b0d77d3158dc019dbe%3A1577766627; _gat_gtag_UA_114322073_1=1; Hm_lpvt_c1a64f283dcc84e35f0a947fcf3b8594=1577795524");

		StringBuffer params = new StringBuffer();
		// 表单参数与get形式一样
		params.append("login-subject").append("=").append("missedubefore")
				.append("&").append("password").append("=").append("longfei19880")
				.append("&").append("auth_token").append("=").append(token);

		System.out.println(params.toString());
		byte[] bypes = params.toString().getBytes();
		httpURLConnection.getOutputStream().write(bypes);// 输入参数
		InputStream inStream = httpURLConnection.getInputStream();
		//System.out.println(new String(StreamTool.readInputStream(inStream), "gbk"));

		while ((length = inStream.read(b)) != -1) {
			stringBuffer.append(new String(b, 0, length, "utf-8"));
		}
		if (inputStream != null) {
			inputStream.close();
		}
		result = stringBuffer.toString();

		System.out.println(result);

		for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("Set-Cookie")) {
				cookieVal = httpURLConnection.getHeaderField(i);
				cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
				sessionId = sessionId + cookieVal + ";";
			}
		}

		System.out.println(cookieVal);

		//return cookieVal;

		Map<String,String> resultMap = Maps.newHashMap();
		resultMap.put("token",token);
		resultMap.put("session",cookieVal);
		//return resultMap;

	}
}
