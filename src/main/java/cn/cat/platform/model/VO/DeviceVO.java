package cn.cat.platform.model.VO;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @createTime 2020/4/17 2:22
 * @description TODO
 */
@Data
public class DeviceVO {
    /**
     * 主键
     */
    private Integer deviceId;

    /**
     * (外键，指向t_user的user_id字段)
     */
    private Integer userId;

    /**
     * 设备编号
     */
    private String deviceNumber;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 备注
     */
    private String message;




}
