package cn.yangtengfei.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static void createTextImage(String text,String path){
        int imageWidth = 300;  //图片的宽度
        int imageHeight = 300; //图片的高度
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        for(int i=60; i<1000; i+=100){
            int width = i;
            int height = i;
            Graphics title = image.createGraphics();
            title.setColor(Color.lightGray);
            //设置字体
            Font titleFont = new Font("微软雅黑", Font.CENTER_BASELINE, 20);
            title.setFont(titleFont);
            FontMetrics fm = title.getFontMetrics();
            int stringWidth = fm.stringWidth("51结果");
            int stringAscent = fm.getAscent();
            int xCoordinate = width/2 - stringWidth/2;
            int yCoordinate = height/2 +stringAscent/2;
            title.drawString("51结果", xCoordinate, yCoordinate);
        }


        Graphics title2 = image.createGraphics();
        title2.setColor(new Color(	65,105,225));
        Font titleFont2 = new Font("微软雅黑", Font.PLAIN, 40);
        title2.setFont(titleFont2);
        FontMetrics fm2 = title2.getFontMetrics();
        int stringWidth2 = fm2.stringWidth(text);
        int stringAscent2 = fm2.getAscent();
        int xCoordinate2 = imageWidth/2 - stringWidth2/2;
        int yCoordinate2 = imageHeight/2 +stringAscent2/2;
        title2.drawString(text, xCoordinate2, yCoordinate2);

        BufferedOutputStream bos = null;
        if(image != null){
            try {
                FileOutputStream fos = new FileOutputStream(path);
                bos = new BufferedOutputStream(fos);

                //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                //encoder.encode(image);
                ImageIO.write(image,"jpg",fos);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(bos!=null){//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
