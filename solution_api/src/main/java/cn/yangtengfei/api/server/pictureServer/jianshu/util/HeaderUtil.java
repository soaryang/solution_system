package cn.yangtengfei.api.server.pictureServer.jianshu.util;

import java.net.HttpURLConnection;
import java.util.Map;

public class HeaderUtil {

	/**
	 * 设置header头部
	 *
	 * @param httpURLConnection
	 * @param headerParams
	 */
	public static void setHeader(HttpURLConnection httpURLConnection, Map<String, String> headerParams) {
		if (headerParams == null || headerParams.size() < 1) {
			return;
		}
		headerParams.entrySet().forEach(headerParam -> {
			httpURLConnection.setRequestProperty(headerParam.getKey(), headerParam.getValue());
		});
	}

}
