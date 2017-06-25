package cn.yangtengfei.webCrawler.stackOverFlow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

@Service
public class StacKOverFlowDataCrwaler {
    public static void main(String[] args) {
        int i = 0;
        int size = 144;
        while (i < 1440) {

            int end = i+144;
            int begin =i+1;
            i = i+144;
            System.out.println(begin+"=============");
        }
    }

    public List<String> getTagList(int begin,int end) {
        List<String> tagList = new ArrayList<>();
        for (int i = begin; i <= end; i++) {
            String url = "https://stackoverflow.com/tags?page=" + i;
            System.out.print("url:" + url);
            try {
                Thread.sleep(5000);
                String str = HttpClientUtil.sendHttpGet(url);
                Document doc = Jsoup.parse(str);
                Elements TagElements = doc.getElementsByAttributeValue("id", "tags-browser");
                for (Element element : TagElements) {
                    Elements elementTrs = element.getElementsByTag("tr");
                    for (Element elementTr : elementTrs) {
                        Elements elementTds = element.getElementsByTag("td");
                        for (Element elementTd : elementTds) {
                            Elements elementFirsts = elementTd.getElementsByTag("a");
                            String tagName = elementFirsts.get(0).html();

                            if (tagName.lastIndexOf(">") != -1) {
                                tagName = tagName.substring(tagName.lastIndexOf(">") + 1);
                            }
                            tagList.add(tagName);
                        }
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return tagList;
    }
}
