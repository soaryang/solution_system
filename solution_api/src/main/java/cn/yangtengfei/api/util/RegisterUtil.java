package cn.yangtengfei.api.util;

import java.util.Random;

public class RegisterUtil {

    public static final String numberLowerLetterChar = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String getRegisterCode(){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 8; i++) {
            sb.append( numberLowerLetterChar.charAt( random.nextInt( numberLowerLetterChar.length() ) ) );
        }
        return sb.toString();
    }
}
