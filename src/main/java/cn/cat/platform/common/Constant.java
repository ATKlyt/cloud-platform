package cn.cat.platform.common;

import java.util.Collections;

/**
 * @author linlt
 * @CreateTime 2020/3/7 17:24
 */
public class Constant {
    public static final Integer PAGE_SIZE = 10;



    /**
     * roleName
     */
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String ROLE_REGIONAL_AGENT = "regionalAgent";
    public static final Integer ROLE_ID_ADMIN = 1;
    public static final Integer ROLE_ID_USER = 2;
    public static final Integer ROLE_ID_REGIONAL_AGENT = 3;


    public static final String NOT_ONLINE_STATUS = "0";
    public static final String ONLINE_STATUS = "1";



    public static final String SUCCESS_CODE = "0";
    public static final String ERROR_CODE = "1";

    public static final String delimiter = ",cloud-platform,";


    /**
     * 指向文件存储路径
     */
    //public static final String UPLOAD_PATH = "/usr/local/project/uploads";
    public static final String UPLOAD_PATH = "/cloud-platform/uploads";
}
