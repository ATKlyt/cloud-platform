package cn.cat.platform.common;

import cn.cat.platform.enums.ResultCode;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;

/**
 * @author linlt
 * @CreateTime 2020/3/4 9:36
 */
@Setter
@Getter
public class Result {

    private Integer code;

    private String message;

    /**
     * fastjson解析数据时，如果某个对象未null，则该属性会被过滤掉
     * SerializerFeature.WriteMapNullValue展示为null的值，默认是不展示的
     */
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue})
    private Object data;

}
