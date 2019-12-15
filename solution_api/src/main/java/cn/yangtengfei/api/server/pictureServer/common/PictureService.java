package cn.yangtengfei.api.server.pictureServer.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class PictureService {

	public abstract void savePictureToServer(String path);

}
