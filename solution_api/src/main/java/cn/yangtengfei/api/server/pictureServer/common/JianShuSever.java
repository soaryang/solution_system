package cn.yangtengfei.api.server.pictureServer.common;

import cn.yangtengfei.api.server.pictureServer.jianshu.bean.PictureToken;
import cn.yangtengfei.api.server.pictureServer.jianshu.bean.PitureUploadResponse;
import cn.yangtengfei.api.server.pictureServer.jianshu.util.FileUtils;
import cn.yangtengfei.api.util.http.HttpObjectResponse;
import cn.yangtengfei.api.util.http.HttpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("JianshuServer")
public class JianShuSever extends PictureService{

	static final String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920";  // 定义数据分隔线

	public static final String GET_PICTURE_TOKEN = "https://www.jianshu.com/upload_images/token.json?filename=%s";

	public static final String SAVE_PICTURE_TO_SERVER = "https://upload.qiniup.com/";

	public static final String COOKIE = "read_mode=day; default_font=font2; locale=zh-CN; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575463378; sajssdk_2015_cross_new_user=1; __yadk_uid=oDQcYpRbxfEQf8MOULmtp5b3dZLPp0x5; remember_user_token=W1sxNjY1MjYwOV0sIiQyYSQxMSQ5S0pmZGE5Z09zb2xVUE1FVHh1eVp1IiwiMTU3NTQ2MzQzNy41MTA3MjUzIl0%3D--712e46287787e383cd6b936959dd0990e4ee485a; _m7e_session_core=ef84a42291bc6049973577839a7bd22b; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216ed0ef5f84789-0eeb17970a6ba9-3b65410e-1049088-16ed0ef5f8525c%22%2C%22%24device_id%22%3A%2216ed0ef5f84789-0eeb17970a6ba9-3b65410e-1049088-16ed0ef5f8525c%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E8%87%AA%E7%84%B6%E6%90%9C%E7%B4%A2%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.baidu.com%2Flink%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%7D%2C%22first_id%22%3A%22%22%7D; Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575463523";

	@Override
	public void savePictureToServer(MultipartFile file) throws Exception {
		//获取image 长传token
		String path = "D:\\tmp\\download.jpg";
		String fileName = file.getOriginalFilename();
		PictureToken pictureToken = HttpObjectResponse.getObject(PictureToken.class, String.format(GET_PICTURE_TOKEN, fileName),"GET",COOKIE);
		System.out.println(pictureToken);
		String suffix = FileUtils.stringSuffix(path);
		String[] fileArray1 = {"file", fileName, "image/" + suffix, path};
		List<String[]> fileParams = new ArrayList<>();
		fileParams.add(fileArray1);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("token", pictureToken.getToken());
		paramMap.put("key", pictureToken.getKey());
		paramMap.put("name", "x:protocol");

		String pictureUploadResult = savePictureToServer(paramMap, fileParams, new FileInputStream(new File(path)),COOKIE);
		PitureUploadResponse pitureUploadResponse = JSON.parseObject(pictureUploadResult, PitureUploadResponse.class);
		//System.out.println(pitureUploadResponse);
		String insertUrl = pitureUploadResponse.getUrl();

		log.info("insertUrl:{}",insertUrl);
	}
	public static String savePictureToServer(Map<String, String> paramMap, List<String[]> fileParams, FileInputStream fileInputStream,String cookie) throws Exception {
		HttpURLConnection httpURLConnection = HttpUtils.initHttpURLConnection(SAVE_PICTURE_TO_SERVER,"POST",cookie);
		httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		byte[] resultByte = saveFile(httpURLConnection, paramMap, fileParams, fileInputStream);
		return new String(resultByte, "UTF-8");
	}

	public static byte[] saveFile(HttpURLConnection httpURLConnection, Map<String, String> paramMap, List<String[]> fileParams, FileInputStream fileInputStream) throws Exception {
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
		System.out.println(value);
		return res;
	}




}
