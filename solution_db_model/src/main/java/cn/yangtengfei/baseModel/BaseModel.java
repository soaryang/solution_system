package cn.yangtengfei.baseModel;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuwei on 16/6/13.
 */

public class BaseModel implements Serializable{

    private static final long serialVersionUID = -6248673946677888658L;

    private Integer deleteFlg = 0;

    private String createUserId;

    private Date createTime = new Date();

    private String updateUserId;

    private Date updateTime = new Date();

}
