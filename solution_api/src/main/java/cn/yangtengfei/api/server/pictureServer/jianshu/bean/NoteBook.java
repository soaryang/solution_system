package cn.yangtengfei.api.server.pictureServer.jianshu.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoteBook implements Serializable {
	private long id;

	private String name;

	private int seq;


}
