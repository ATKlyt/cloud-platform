package cn.cat.platform.common;

/**
 * @author linlt
 * @createTime 2020/7/14 8:32
 * @description TODO
 */
public class SecurityConstant {
    /**
     * security
     */
    public static final String AUTHORITIES = "authorities";
    public static final String SECRET_KEY = "cloud-platform";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    // 过期时间，单位毫秒：一个小时
    public static final int EXPIRE_TIME = 60 * 60 * 1000;
    // token刷新间隔5分钟
    public static final int TOKEN_REFRESH_INTERVAL = 300 * 12 * 24 * 7;

    public static final String TOKEN_INVALID_STATUS = "0";
    public static final String TOKEN_VALID_STATUS = "1";

    public static final String CONTEXT_PATH = "/cloud-platform";
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String METHOD_POST = "POST";
}
