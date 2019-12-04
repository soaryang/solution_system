package cn.yangtengfei.api.server.pictureServer.jianshu.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Note implements Serializable {

	private long id;

	private String title;

	private int autosave_control;

	private String content;
}
