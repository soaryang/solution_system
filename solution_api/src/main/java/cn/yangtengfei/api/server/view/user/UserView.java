package cn.yangtengfei.api.server.view.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private String verifyCode;

    private String openId;

    private Integer subscribeState;

    private String roleId;


    private List<RoleView> roleViewList;


}
