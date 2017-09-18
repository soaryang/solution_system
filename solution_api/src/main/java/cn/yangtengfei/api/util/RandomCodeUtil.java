package cn.yangtengfei.api.util;

import cn.yangtengfei.util.DateUtils;

import java.util.Date;
import java.util.Random;

public class RandomCodeUtil {


    public static final String numberLowerLetterChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getCode(){

        Date date = DateUtils.getCurrentDate();
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 8; i++) {
            sb.append( numberLowerLetterChar.charAt( random.nextInt( numberLowerLetterChar.length())));
        }
        String result =  sb.toString();
        return result;
    }
}
