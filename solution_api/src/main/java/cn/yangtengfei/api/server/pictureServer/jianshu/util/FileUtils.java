package cn.yangtengfei.api.server.pictureServer.jianshu.util;

import java.io.File;

public class FileUtils {

	public static String stringSuffix(String path) {
		File file = new File(path);
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return suffix;
	}

	public static String getFileName(String path) {
		File file = new File(path);
		String fileName = file.getName();
		return fileName;
	}

}
