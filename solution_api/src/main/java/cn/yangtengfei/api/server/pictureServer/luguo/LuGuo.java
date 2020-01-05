package cn.yangtengfei.api.server.pictureServer.luguo;

import cn.yangtengfei.api.server.pictureServer.jianshu.util.FileUtils;
import cn.yangtengfei.api.util.http.HttpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

@Slf4j
@Service
public class LuGuo {

	static final String BOUNDARY = "------WebKitFormBoundaryQx3F1tuoin1schAn";  // 定义数据分隔线

	public static final String SAVE_PICTURE_TO_SERVER = "https://imgchr.com/json";

	public static void main(String[] args) {
		try {
			Map<String,String> userMap = LuGuoDengLu.session();
			String session = "";
			String token = userMap.get("token");
			HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection(SAVE_PICTURE_TO_SERVER);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);


			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Host", "imgchr.com");
			httpURLConnection.setRequestProperty("Connection", "keep-alive");
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestProperty("Origin", "https://imgchr.com");
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			httpURLConnection.setRequestProperty("Referer", "https://imgchr.com/i/l36QgS");
			httpURLConnection.setRequestProperty("Cookie","_ga=GA1.2.490248484.1576587968; PHPSESSID=g8jeplrikv9j5i4akudvam49g6; Hm_lvt_c1a64f283dcc84e35f0a947fcf3b8594=1577449018,1577706763,1577706788,1577795225; _gid=GA1.2.18614959.1577795225; KEEP_LOGIN=eatg0%3A2cf74704e5f4ea6bda3eeac71a0338ebe0d33b2728b82c268b3b07a8622092d45bd22467902f81c970bb61317457349f3f2be12718582988738abf182a3c4c5727e65226795bb6bfe6aa6f3279d02e2a1b7b430db60da05b3c1571f5325ababd7e7ac9417f48697ed66b0d77d3158dc019dbe%3A1577766627; _gat_gtag_UA_114322073_1=1; Hm_lpvt_c1a64f283dcc84e35f0a947fcf3b8594=1577795524");

			String path = "D:/01.png";
			String fileName = FileUtils.getFileName(path);
			String suffix = FileUtils.stringSuffix(path);
			String[] fileArray1 = {"source", fileName, "image/" + suffix, path};
			List<String[]> fileParams = new ArrayList<>();
			fileParams.add(fileArray1);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("type", "file");
			paramMap.put("action", "upload");
			paramMap.put("timestamp", (new Date()).getTime()+"");
			paramMap.put("auth_token",token);
			paramMap.put("nsfw", "0");
			String urlPath = saveFile(httpURLConnection, paramMap, fileParams, new FileInputStream(new File(path)));
			System.out.println(urlPath);
		} catch (Exception e) {
			log.error("uploadPictureToAliServer error", e);
		}

	}

	public static String saveFile(HttpURLConnection httpURLConnection, Map<String, String> paramMap, List<String[]> fileParams, FileInputStream fileInputStream) throws Exception {
		ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息
		InputStream is = null;//输入流，用来读取服务器返回的信息
		byte[] res = null;//保存服务器返回的信息的byte数组
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
					while ((bytes = fileInputStream.read(bufferOut)) != -1) {
						outputStream.write(bufferOut, 0, bytes);
					}
					if (i < num - 1) {
						outputStream.write(endBoundary.getBytes("utf-8"));
					}
					fileInputStream.close();
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

		String value = new String(res, "UTF-8");
		log.info("result:{}",value);
		Map maps = (Map) JSON.parse(res);
		String url = StringUtils.EMPTY;
		if (maps.get("imgurl") != null) {
			url = String.valueOf(maps.get("imgurl"));
		}
		return url;
	}

}
