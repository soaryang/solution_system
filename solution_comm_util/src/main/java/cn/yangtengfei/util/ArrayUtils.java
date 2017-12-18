package cn.yangtengfei.util;

public class ArrayUtils {

    public static boolean checkArrayIsNull(String ... array){
        if(array==null || array.length==0){
            return true;
        }
        return false;
    }

    public static boolean checkArrayIsNotNull(String ... array){
        if(array!=null && array.length!=0){
            return true;
        }
        return false;
    }

}
