package cn.yangtengfei.api.server.pictureServer.luguo;

import cn.yangtengfei.api.config.PageResultModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class LuGuoImageResponse implements Serializable {

	private int status_code;

	private Success success;

	private Image image;
}

@Data
class Image implements Serializable{
	private UploadFile file;
}

@Data
class UploadFile implements Serializable{
	private UploadFileResource resource;
}

@Data
class UploadFileResource implements Serializable{
	private String type;
	private UploadFileResourceChain chain;
}

@Data
class UploadFileResourceChain implements Serializable{
	private String image;
	private String thumb;
}


@Data
class Success implements Serializable{
	private String message;

	private int code;
}
