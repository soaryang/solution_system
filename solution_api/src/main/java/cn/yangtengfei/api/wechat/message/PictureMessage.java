package cn.yangtengfei.api.wechat.message;


import lombok.Data;

@Data
public class PictureMessage extends BaseMessage {
    private String PicUrl;

    private String MsgId;

}
