package cn.yangtengfei.api.util;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Administrator on 2017/6/3 0003.
 */
public class SpecialString {
    public static void demo(){


        System.out.println("转义HTML,注意汉字:"+StringEscapeUtils.escapeHtml4("<font>chen磊  xing</font>"));    //转义HTML,注意汉字
        System.out.println("反转义HTML:"+StringEscapeUtils.unescapeHtml4("<font>chen磊  xing</font>"));  //反转义HTML

        System.out.println("转成Unicode编码："+StringEscapeUtils.escapeJava("陈磊兴"));     //转义成Unicode编码

        System.out.println("转义XML："+StringEscapeUtils.escapeXml("<name>陈磊兴</name>"));   //转义xml
        System.out.println("反转义XML："+StringEscapeUtils.unescapeXml("<name>陈磊兴</name>"));    //转义xml

    }
}
