package cn.yangtengfei.api.config.constant;

public enum  PictureSource {
	JIANSHU(1,"jianshu","简书");
	private int source;
	private String name;
	private String desc;

	PictureSource(int source,String name,String desc){
		this.source = source;
		this.name = name;
		this.desc = desc;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
