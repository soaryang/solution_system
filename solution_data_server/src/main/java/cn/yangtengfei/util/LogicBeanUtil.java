package cn.yangtengfei.util;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.view.tag.TagView;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class LogicBeanUtil {

    public static List<TagView> copyListToAimList(List<Tag> sourceList, List<TagView> aimList){
        if(ListUtils.checkListIsNotNull(sourceList)){
            aimList = new ArrayList<TagView>();
            for(Tag o:sourceList){
                TagView tempObject = new TagView();
                BeanUtils.copyProperties(o,tempObject);
                tempObject.setUrl("https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=05f46e0580d4b31cf03c93bdbfed4042/2cf5e0fe9925bc31137974de55df8db1cb13704b.jpg");
                aimList.add(tempObject);
            }
            return aimList;
        }
        return null;
    }
}
