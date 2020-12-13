package cn.cat.platform.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/9 20:30
 * @description TODO
 */
public class JsonUtil {
    /**
     * 将对象转换成json字符串
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        return JSON.toJSONString(data);
    }

    /**
     * 将json字符串转化为对象
     * @param jsonStr
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String jsonStr, Class<T> beanClass) {
        return JSON.parseObject(jsonStr, beanClass);
    }

    /**
     * 将json字符串转换成对象list
     * @param jsonStr
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> beanClass) {
        return JSON.parseArray(jsonStr, beanClass);
    }

}
