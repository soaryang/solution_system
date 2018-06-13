package cn.tst;

import cn.yangtengfei.Application;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import cn.yangtengfei.model.lottery.DoubleColorBall;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.lottery.DoubleColorBallService;
import cn.yangtengfei.webCrawler.stackOverFlow.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.tools.Tool;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;

/**
 * Created by Administrator on 2017/1/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class TagTest {


    @Autowired
    private DoubleColorBallService doubleColorBallService;

    @Test
    public void showData() throws NoSuchFieldException, IllegalAccessException {

        /*List<DoubleColorBall> doubleColorBallServiceAll = doubleColorBallService.findAll();
        for(DoubleColorBall doubleColorBall:doubleColorBallServiceAll){
            for(int i=1; i<=33; i++){
                System.out.print(getValue(doubleColorBall,"red_color_"+i));
                System.out.print("\t\t\t\t\t\t");
            }
            System.out.println();
        }*/
        //for(DoubleColorBall doubleColorBall:doubleColorBallServiceAll){
        /*for(int i=0; i<doubleColorBallServiceAll.size(); i++){
            DoubleColorBall doubleColorBall = doubleColorBallServiceAll.get(i);
            int code  = doubleColorBall.getCode();
            System.out.print("当前编号:"+code);
            System.out.println();
            long tempSum = doubleColorBallService.countlteCode(code);
            for(int z=1; z<=33; z++) {
                long count = doubleColorBallService.count("red_color_" + z, code);
                System.out.print(count);
                System.out.print("\t\t\t\t\t\t");
            }
            System.out.println();

            for(int j=1; j<=33; j++){
                long count = doubleColorBallService.count("red_color_"+j,code);
                float num = (float)count/tempSum;
                DecimalFormat df = new DecimalFormat("0.000000");//保留4位小数
                String s = df.format(num*100);
                //System.out.println(i+"出现的次数"+":"+count+"  出现的概率:"+s+"%");
                System.out.print(s+"%");
                System.out.print("\t\t\t\t");
            }
            System.out.println();
        }*/

        long sum = doubleColorBallService.sum();
        System.out.println("彩票总期数:"+sum);

        List<DoubleColorBall> doubleColorBallServiceAll = doubleColorBallService.findAll();
        DoubleColorBall doubleColorBall = doubleColorBallServiceAll.get(doubleColorBallServiceAll.size()-1);


         /*for(int i=1; i<=33; i++){
            System.out.print(i);
            System.out.print("\t\t\t\t\t\t");
        }
        System.out.println();

       for(int j=1; j<=33; j++){
            long count = doubleColorBallService.count("red_color_"+j);
            System.out.print(count);
            System.out.print("\t\t\t\t\t\t");
        }
        System.out.println();*/

        HashMap<String,Double> map = new HashMap<String,Double>();
        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> stringDoubleTreeMap = new TreeMap<String,Double>(bvc);
        for(int i=1; i<=33; i++){
            String key = "red_color_"+i;
            long count  = doubleColorBallService.count(key);
            float num = (float)count/sum;
            DecimalFormat df = new DecimalFormat("0.000000");//保留4位小数
            String s = df.format(num*100);



            Object colorValue =  getValue(doubleColorBall,"red_color_"+i);
            double myDouble = Double.parseDouble(s);
            map.put(i+"",myDouble);
            if(Integer.parseInt(colorValue+"")!=0){
                System.out.print(i+"\t\t\t\t");
                System.out.print(count +"\t\t\t\t");
                //System.out.println(i+"出现的次数"+":"+count+"  出现的概率:"+s+"%");
                System.out.print(myDouble+"%");
                System.out.println();
            }
        }
        stringDoubleTreeMap.putAll(map);
        System.out.println();
        System.out.println("stringDoubleTreeMap: "+stringDoubleTreeMap);
        System.out.println();

    }

    public void getData() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        for(int i=2003; i<=2018; i++){
            Thread.sleep(10000);
            String result = HttpClientUtil.sendHttpGet("http://www.55128.cn/zs/4_26.htm?startTerm="+i+"001&endTerm="+i+"400");
            Document doc = Jsoup.parse(result);
            Element content = doc.getElementById("chartData");
            Elements links = content.getElementsByTag("tr");
            System.out.println("====================="+links.size());
            for(Element element:links){
                analysizeTr(element);
            }
        }
    }

    private void  analysizeTr(Element element) throws NoSuchFieldException, IllegalAccessException {
        Elements links  = element.getElementsByTag("td");
        System.out.println("====================="+links.size());
        DoubleColorBall doubleColorBall = new DoubleColorBall();
        for(int i=0; i<links.size(); i++){
            Element tempElment = links.get(i);
            int ball = 0;
            if(i==0){
                //System.out.print("日期："+links.get(i).html());
                //System.out.print("\t");
                String code = links.get(i).html();
                setValue(doubleColorBall,"code",Integer.parseInt(code));

            }else if(i>=1 && i<=33){
                if(StringUtils.isNoneBlank(tempElment.getElementsByClass("ball-yred").html())){
                    //System.out.print(tempElment.getElementsByClass("ball-yred").html());
                    //System.out.print("\t");
                    String ballStr = tempElment.getElementsByClass("ball-yred").html();
                    ball = Integer.parseInt(ballStr);

                    setValue(doubleColorBall,"red_color_"+i,ball);
                }
            }else{
                if(StringUtils.isNoneBlank(tempElment.getElementsByClass("ball-gblue").html())){
                    //System.out.print(tempElment.getElementsByClass("ball-gblue").html());
                    //System.out.print("\t");
                    String ballStr = tempElment.getElementsByClass("ball-gblue").html();
                    ball = Integer.parseInt(ballStr);
                    int index = i-33;
                    setValue(doubleColorBall,"blue_color_"+index,ball);
                }
            }
        }
        System.out.print(doubleColorBall);

        doubleColorBallService.save(doubleColorBall);
        System.out.println();
    }

    /*public static Object getProperty(Object obj, String propertyName) {
        DoubleColorBall student=new DoubleColorBall();//Student{name='三毛'}
        System.out.println(student);
        Tool t=new tool();
        t.setProperty(student,"name","永超");//Student{name='永超'}
        System.out.println(student);
    }*/


    private String getValue(Object obj,String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Class clazz=obj.getClass();   //获取字节码对象
        Field field=clazz.getDeclaredField(propertyName); //暴力反射获取字段
        field.setAccessible(true);
        String value =  field.get(obj).toString();
        return value;
    }

    private void setValue(Object obj,String propertyName, Object value) throws IllegalAccessException, NoSuchFieldException {
        Class clazz=obj.getClass();   //获取字节码对象
        Field field=clazz.getDeclaredField(propertyName); //暴力反射获取字段
        field.setAccessible(true); //设置访问权限
        field.set(obj,value);   //设置值
    }

    /*@Autowired
    private ApiTagService apiTagService;

    @Autowired
    private ApiUserService apiUserService;

    @Test
    public void findParty(){
        UserView userView = new UserView();
        userView.setName("admin");
        userView.setPassword("123");
        apiUserService.saveUserByUserView(userView);
    }*/
    /*@Test
    public void findParty(){
        TagView tagView = new TagView();
        tagView.setName("springboo22222t");
        //tagView = apiTagService.save(tagView);
        System.out.println("================="+ JSON.toJSONString(tagView));
    }

    @Test
    public void updateTag(){
       Tag tag =apiTagService.updateUseStatus("591e5c6c54815a0ea114ff23",0,"asdasdasdasd");
        System.out.println("================="+ JSON.toJSONString(tag));
    }*/
}

class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    //这里需要将要比较的map集合传进来
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    //比较的时候，传入的两个参数应该是map的两个key，根据上面传入的要比较的集合base，可以获取到key对应的value，然后按照value进行比较
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}
