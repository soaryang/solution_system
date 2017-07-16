package cn.yangtengfei.api.view.user;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class UserView implements Serializable {

    private static final long serialVersionUID = -6606617919604569738L;


    private String id;

    private String name;

    private String email;

    private String password;

    private String openId;

    private Integer subscribeState;


}
