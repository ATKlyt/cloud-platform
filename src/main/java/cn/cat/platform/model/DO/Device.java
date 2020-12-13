package cn.cat.platform.model.DO;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @CreateTime 2020/3/4 4:19
 */
@Data
public class Device implements Serializable {
    /**
    * 主键
    */
    private Integer deviceId;

    /**
    * (外键，指向t_user的user_id字段)
    */
    @NotNull(message = "设备所属用户不能为空")
    private Integer userId;

    /**
    * 设备编号
    */
    @NotNull(message = "设备编号不能为空")
    private String deviceNumber;

    /**
    * 设备名称
    */
    @NotNull(message = "设备名称不能为空")
    private String deviceName;

    /**
    * 备注
    */
    private String message;

    private static final long serialVersionUID = 1L;
}