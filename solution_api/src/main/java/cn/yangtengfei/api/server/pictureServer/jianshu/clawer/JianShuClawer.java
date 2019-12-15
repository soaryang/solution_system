package cn.yangtengfei.api.server.pictureServer.jianshu.clawer;

import cn.yangtengfei.api.server.pictureServer.jianshu.bean.Note;
import cn.yangtengfei.api.server.pictureServer.jianshu.bean.NoteBook;
import cn.yangtengfei.api.server.pictureServer.jianshu.bean.PictureToken;
import cn.yangtengfei.api.server.pictureServer.jianshu.bean.PitureUploadResponse;
import cn.yangtengfei.api.server.pictureServer.jianshu.util.FileUtils;
import cn.yangtengfei.util.DateUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

@Service
public class JianShuClawer {

	public static final String NOTE_BOOK_LIST_URL = "https://www.jianshu.com/author/notebooks";

	public static final String NOTE_LIST_URL = "https://www.jianshu.com/author/notebooks/%s/notes";

	public static final String CREATE_NOTE_URL = "https://www.jianshu.com/author/notes";

	public static final String WRITE_INFO_TO_NOTE_URL = "https://www.jianshu.com/author/notes/%s";

	public static final String GET_PICTURE_TOKEN = "https://www.jianshu.com/upload_images/token.json?filename=%s";

	public static final String SAVE_PICTURE_TO_SERVER = "https://upload.qiniup.com/";

	public static final String NOTE_CONTENT_URL = "https://www.jianshu.com/author/notes/%s/content";

	static final String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920";  // 定义数据分隔线

	public static final String COOKIE = "read_mode=day; default_font=font2; locale=zh-CN; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575463378; sajssdk_2015_cross_new_user=1; __yadk_uid=oDQcYpRbxfEQf8MOULmtp5b3dZLPp0x5; remember_user_token=W1sxNjY1MjYwOV0sIiQyYSQxMSQ5S0pmZGE5Z09zb2xVUE1FVHh1eVp1IiwiMTU3NTQ2MzQzNy41MTA3MjUzIl0%3D--712e46287787e383cd6b936959dd0990e4ee485a; _m7e_session_core=ef84a42291bc6049973577839a7bd22b; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216ed0ef5f84789-0eeb17970a6ba9-3b65410e-1049088-16ed0ef5f8525c%22%2C%22%24device_id%22%3A%2216ed0ef5f84789-0eeb17970a6ba9-3b65410e-1049088-16ed0ef5f8525c%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E8%87%AA%E7%84%B6%E6%90%9C%E7%B4%A2%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.baidu.com%2Flink%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%7D%2C%22first_id%22%3A%22%22%7D; Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1575463523";


	public  void executeUpload() throws Exception {
		//获取bookList 列表
		List<NoteBook> noteBookList = getEList(NoteBook.class, NOTE_BOOK_LIST_URL);
		System.out.println(noteBookList);
		long bookId = 0;
		for (NoteBook noteBook : noteBookList) {
			if ("日记".equals(noteBook.getName())) {
				bookId = noteBook.getId();
			}
		}
		if (bookId == 0) {
			return;
		}
		Thread.sleep(5000);
		List<Note> noteList = getEList(Note.class, String.format(NOTE_LIST_URL, bookId));
		System.out.println(noteList);


		//获取note信息
		String title = DateUtils.converDateToStr(new Date(), DateUtils.PATTERN_YYYY_MM_DD) + "号日记";
		boolean isExist = false;
		Note noteTemp = null;
		for (Note note : noteList) {
			if (title.equals(note.getTitle())) {
				isExist = true;
				noteTemp = note;
				break;
			}
		}
		if (!isExist) {
			Thread.sleep(5000);
			//如果记事本不存在创建一个记事本
			String noteResult = createNote(bookId, title, false);
			Note note = JSON.parseObject(noteResult, Note.class);
			noteTemp = note;
		}
		Thread.sleep(5000);
		Note note = getObject(Note.class, String.format(NOTE_CONTENT_URL, noteTemp.getId()));
		System.out.println(note);
		List<String> imageList = new ArrayList<>();
		if (note != null && StringUtils.isNotBlank(note.getContent())) {
			Document document = Jsoup.parse(note.getContent());
			Elements elements = document.getElementsByTag("img");
			if (CollectionUtils.isNotEmpty(elements)) {
				for (Element element : elements) {
					String src = element.attr("src");
					imageList.add(src.split("[?]")[0]);
				}
			}
		}
		//获取image 长传token
		String path = "D:\\tmp\\download.jpg";
		String fileName = FileUtils.getFileName(path);
		PictureToken pictureToken = getObject(PictureToken.class, String.format(GET_PICTURE_TOKEN, fileName));
		System.out.println(pictureToken);
		String suffix = FileUtils.stringSuffix(path);
		String[] fileArray1 = {"file", fileName, "image/" + suffix, path};
		List<String[]> fileParams = new ArrayList<>();
		fileParams.add(fileArray1);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("token", pictureToken.getToken());
		paramMap.put("key", pictureToken.getKey());
		paramMap.put("name", "x:protocol");

		String pictureUploadResult = savePictureToServer(paramMap, fileParams, new FileInputStream(new File(path)));
		PitureUploadResponse pitureUploadResponse = JSON.parseObject(pictureUploadResult, PitureUploadResponse.class);
		//System.out.println(pitureUploadResponse);
		String insertUrl = pitureUploadResponse.getUrl();
		if (StringUtils.isNotBlank(insertUrl)) {
			imageList.add(insertUrl);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", noteTemp.getId());
		map.put("autosave_control", noteTemp.getAutosave_control() + 1);
		map.put("title", title);
		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer.append("<p>" + title + "我今天吃了啥，郁闷</p>");
		for (int i = 0; i < imageList.size(); i++) {
			contentBuffer.append("<p><br/></p>");
			contentBuffer.append("<div class=\"image-package\">");
			contentBuffer.append("<img class=\"uploaded-img\" src=\""+imageList.get(0)+"\" width=\"auto\" height=\"auto\" />");
			contentBuffer.append("</div>");
		}
		map.put("content", contentBuffer.toString());
		Thread.sleep(5000);
		writeInfoToNote(noteTemp.getId(), map);
	}

	public static String savePictureToServer(Map<String, String> paramMap, List<String[]> fileParams, FileInputStream fileInputStream) throws Exception {
		HttpURLConnection httpURLConnection = initHttpURLConnection(SAVE_PICTURE_TO_SERVER);
		setHeader(httpURLConnection, "POST", COOKIE);
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


	/**
	 * 将信息写入note 中
	 *
	 * @param noteId
	 * @param contentMap
	 * @return
	 * @throws IOException
	 */
	public static String writeInfoToNote(long noteId, Map<String, Object> contentMap) throws IOException {

		HttpURLConnection httpURLConnection = initHttpURLConnection(String.format(WRITE_INFO_TO_NOTE_URL, noteId));
		setHeader(httpURLConnection, "PUT", COOKIE);
		StringBuffer result;
		OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
		System.out.println("write to Server:" + JSON.toJSONString(contentMap));
		writer.write(JSON.toJSONString(contentMap));
		writer.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		result = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		br.close();
		httpURLConnection.disconnect();
		System.out.println("jsonString:" + result.toString());
		return result.toString();
	}


	/**
	 * 创建一个记事本
	 *
	 * @param bookId
	 * @param name
	 * @param at_bottom
	 * @return
	 * @throws IOException
	 */
	public static String createNote(long bookId, String name, boolean at_bottom) throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("notebook_id", bookId);
		map.put("title", name);
		map.put("at_bottom", at_bottom);
		HttpURLConnection httpURLConnection = initHttpURLConnection(CREATE_NOTE_URL);
		setHeader(httpURLConnection, "POST", COOKIE);

		StringBuffer result;
		OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
		System.out.println("write to Server:" + JSON.toJSONString(map));
		writer.write(JSON.toJSONString(map));
		writer.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		result = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		br.close();
		httpURLConnection.disconnect();
		System.out.println("jsonString:" + result.toString());
		return result.toString();
	}


	public static HttpURLConnection initHttpURLConnection(String url) throws IOException {
		System.out.println("initHttpURLConnection url is: " + url);
		URL notebooksUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) notebooksUrl.openConnection();
		return connection;
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

	public static <E> E getObject(Class<E> eClass, String url) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection httpURLConnection = initHttpURLConnection(url);
		setHeader(httpURLConnection, "GET", COOKIE);
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

	public static <E> List<E> getEList(Class<E> eClass, String url) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		HttpURLConnection httpURLConnection = initHttpURLConnection(url);
		setHeader(httpURLConnection, "GET", COOKIE);
		InputStream inputStream = httpURLConnection.getInputStream();
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = inputStream.read(b)) != -1) {
			stringBuffer.append(new String(b, 0, length));
		}
		if (inputStream != null) {
			inputStream.close();
		}
		String result = stringBuffer.toString();
		List<E> noteBookList = JSON.parseArray(result, eClass);
		httpURLConnection.disconnect();
		return noteBookList;
	}


}
