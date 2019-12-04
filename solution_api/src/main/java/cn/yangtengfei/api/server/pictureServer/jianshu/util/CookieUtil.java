package cn.yangtengfei.api.server.pictureServer.jianshu.util;

import java.net.HttpURLConnection;
import java.util.Arrays;

public class CookieUtil {

	public static void setCookie(HttpURLConnection httpURLConnection, String cookieStr) {
		String cookies[] = cookieStr.split(";");
		StringBuffer stringBuffer = new StringBuffer();
		Arrays.stream(cookies).forEach(e -> {
			stringBuffer.append(e);
		});
		httpURLConnection.setRequestProperty("cookie", cookieStr);
	}


}
