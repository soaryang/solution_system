package cn.yangtengfei.model.question;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
@Document(collection = "tag")
public class Tag extends BaseModel {

    private String id;

    private String name;

    private String imagePath;

    private String describe;

    /**未使用：0 ; 使用：1 ；禁用：2*/
    private Integer useStatus;

    /**问题数量*/
    private long questionCount;

    /**文章数量*/
    private long articleCount;

    /**寄存的网络地址*/
    private String netUrl;


    public String getNetUrl() {
        if(StringUtils.isEmpty(netUrl)){
            netUrl = "http://cms.51jieguo.com/images"+imagePath;
        }
        return netUrl;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl;
    }
}
