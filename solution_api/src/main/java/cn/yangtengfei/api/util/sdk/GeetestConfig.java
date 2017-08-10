package cn.yangtengfei.api.util.sdk;

public class GeetestConfig {

	// 填入自己的captcha_id和private_key
    private static final String geetest_id = "e69b39a48d746bc2167eda130586f83d";
	private static final String geetest_key = "610c1db4de27b1d21e64acd71cef44f2";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
