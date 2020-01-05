package cn.yangtengfei.api.server.pictureServer.luguo;

import cn.yangtengfei.api.util.http.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LuGuoImageService {

	static final String BOUNDARY = "------WebKitFormBoundaryQx3F1tuoin1schAn";  // 定义数据分隔线

	public static final String SAVE_PICTURE_TO_SERVER = "https://imgchr.com/json";

	public static final String SESSIONID = "g8jeplrikv9j5i4akudvam49g6";


	public Map<String, String> doLogin() throws IOException {
		return session();
	}

	public String getLuGuoImageUrlPath(String token,String fileName, String contentType, String path, InputStream inputStream) {

		String urlPath = StringUtils.EMPTY;
		try {
			//执行登录获取token
			//Map<String, String> userMap = session();
			//String token = userMap.get("token");

			HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection(SAVE_PICTURE_TO_SERVER);
			setProperties(httpURLConnection, "POST", SESSIONID);
			//String path = "D:/01.png";
			//String fileName = FileUtils.getFileName(path);
			//String suffix = FileUtils.stringSuffix(path);
			String[] fileArray1 = {"source", fileName, contentType, path};
			List<String[]> fileParams = new ArrayList<>();
			fileParams.add(fileArray1);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("type", "file");
			paramMap.put("action", "upload");
			paramMap.put("timestamp", (new Date()).getTime() + "");
			paramMap.put("auth_token", token);
			paramMap.put("nsfw", "0");
			urlPath = saveFile(httpURLConnection, paramMap, fileParams, inputStream);
			System.out.println(urlPath);
			return urlPath;
		} catch (Exception e) {
			log.error("uploadPictureToAliServer error", e);
		}
		return urlPath;
	}

	/*public static void main(String[] args) {
		try {
			Map<String, String> userMap = session();
			String session = "";
			String token = userMap.get("token");
			HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection(SAVE_PICTURE_TO_SERVER);

			setProperties(httpURLConnection, "POST", SESSIONID);
			String path = "D:/01.png";
			String fileName = FileUtils.getFileName(path);
			String suffix = FileUtils.stringSuffix(path);
			String[] fileArray1 = {"source", fileName, "image/" + suffix, ""};
			List<String[]> fileParams = new ArrayList<>();
			fileParams.add(fileArray1);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("type", "file");
			paramMap.put("action", "upload");
			paramMap.put("timestamp", (new Date()).getTime() + "");
			paramMap.put("auth_token", token);
			paramMap.put("nsfw", "0");
			String urlPath = saveFile(httpURLConnection, paramMap, fileParams, new FileInputStream(new File(path)));
			System.out.println(urlPath);
		} catch (Exception e) {
			log.error("uploadPictureToAliServer error", e);
		}
	}*/


	public static String saveFile(HttpURLConnection httpURLConnection, Map<String, String> paramMap, List<String[]> fileParams, InputStream inputStream) throws Exception {
		ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息
		InputStream is = null;//输入流，用来读取服务器返回的信息
		byte[] res = null;//保存服务器返回的信息的byte数组
		String url = StringUtils.EMPTY;
		try {
			OutputStream outputStream = httpURLConnection.getOutputStream();
			////1.先写文字形式的post流
			//头
			String boundary = BOUNDARY;
			//中
			final StringBuffer resSB = new StringBuffer("\r\n");
			//尾
			String endBoundary = "\r\n--" + boundary + "--\r\n";
			paramMap.entrySet().forEach(param -> {
				resSB.append("Content-Disposition: form-data; name=")
						.append(param.getKey()).append("\r\n")
						.append("\r\n").append(param.getValue())
						.append("\r\n").append("--")
						.append(boundary).append("\r\n");
			});
			String boundaryMessage = resSB.toString();
			log.info("boundaryMessage============:" + boundaryMessage);
			//写出流
			outputStream.write(("--" + boundary + boundaryMessage).getBytes("utf-8"));
			//2.再写文件开式的post流
			//fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath
			StringBuffer fileContentBuffer = new StringBuffer();
			if (fileParams != null) {
				for (int i = 0, num = fileParams.size(); i < num; i++) {
					String[] parsm = fileParams.get(i);
					String fileField = parsm[0];
					String fileName = parsm[1];
					String fileType = parsm[2];
					String filePath = parsm[3];
					fileContentBuffer.append("Content-Disposition: form-data; name=").append(fileField).append("; filename=").append(
							fileName).append("\r\n").append("Content-Type: ").append(fileType).append("\r\n\r\n");
					outputStream.write(fileContentBuffer.toString().getBytes("utf-8"));
					log.info("about file:{}", fileContentBuffer.toString());
					//开始写文件
					int bytes = 0;
					byte[] bufferOut = new byte[1024 * 5];
					while ((bytes = inputStream.read(bufferOut)) != -1) {
						outputStream.write(bufferOut, 0, bytes);
					}
					if (i < num - 1) {
						outputStream.write(endBoundary.getBytes("utf-8"));
					}
					inputStream.close();
				}

			}
			//3.最后写结尾
			outputStream.write(endBoundary.getBytes("utf-8"));
			outputStream.close();
			int ch;
			is = httpURLConnection.getInputStream();
			bos = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1) {
				bos.write(ch);
			}
			res = bos.toByteArray();
			String value = new String(res, "UTF-8");
			log.info("result:{}", value);


			LuGuoImageResponse luGuoImageResponse = JSON.parseObject(res, LuGuoImageResponse.class);
			if (luGuoImageResponse != null && luGuoImageResponse.getStatus_code() == 200) {

				Image image = luGuoImageResponse.getImage();
				UploadFile uploadFile = image.getFile();
				UploadFileResource uploadFileResource = uploadFile.getResource();
				UploadFileResourceChain chain = uploadFileResource.getChain();
				url = chain.getThumb();
			}
			/*Map maps = (Map) JSON.parse(res);
			if ("200".equals(maps.get("status_code"))) {
				Map mapImage = (Map) JSON.parse(String.valueOf(maps.get("image")));
				url = String.valueOf(maps.get("imgurl"));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (is != null)
					is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return url;
	}

	public  Map<String, String> session() throws IOException {
		HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection("https://imgchr.com/");
		setProperties(httpURLConnection, "GET", SESSIONID);
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
		httpURLConnection = HttpUtils.initHttpURLConnection("https://imgchr.com/login");
		setProperties(httpURLConnection, "POST", SESSIONID);
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
		Map<String, String> resultMap = Maps.newHashMap();
		resultMap.put("token", token);
		resultMap.put("session", cookieVal);
		return resultMap;
	}

	private static void setProperties(HttpURLConnection httpURLConnection, String method, String sessionId) throws ProtocolException {
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod(method);
		httpURLConnection.setRequestProperty("Host", "imgchr.com");
		httpURLConnection.setRequestProperty("Connection", "keep-alive");
		httpURLConnection.setRequestProperty("Accept", "application/json");
		httpURLConnection.setRequestProperty("Origin", "https://imgchr.com");
		httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
		httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		httpURLConnection.setRequestProperty("Referer", "https://imgchr.com/i/l36QgS");
		httpURLConnection.setRequestProperty("Cookie", "_ga=GA1.2.490248484.1576587968; PHPSESSID=" + sessionId + "; Hm_lvt_c1a64f283dcc84e35f0a947fcf3b8594=1577449018,1577706763,1577706788,1577795225; _gid=GA1.2.18614959.1577795225; KEEP_LOGIN=eatg0%3A2cf74704e5f4ea6bda3eeac71a0338ebe0d33b2728b82c268b3b07a8622092d45bd22467902f81c970bb61317457349f3f2be12718582988738abf182a3c4c5727e65226795bb6bfe6aa6f3279d02e2a1b7b430db60da05b3c1571f5325ababd7e7ac9417f48697ed66b0d77d3158dc019dbe%3A1577766627; _gat_gtag_UA_114322073_1=1; Hm_lpvt_c1a64f283dcc84e35f0a947fcf3b8594=1577795524");
	}

}
