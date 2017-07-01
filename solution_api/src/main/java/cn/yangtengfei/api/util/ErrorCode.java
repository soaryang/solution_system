package cn.yangtengfei.api.util;

public interface ErrorCode {

    public class Auth_Error_Code{
        public static final String NO_ACCESS="001000";
        public static final String USERNAME_IS_ERROR="001001";
        public static final String PASSWORD_IS_ERROR="001002";

        public static final String USERNAME_OR_PASSWORD_IS_NULL="001003";
    }
}
