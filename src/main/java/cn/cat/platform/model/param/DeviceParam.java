package cn.cat.platform.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @createTime 2020/8/2 13:59
 * @description TODO
 */
@ApiModel
@Data
public class DeviceParam {
    /**
     * 主键
     */
    @ApiModelProperty(value = "设备Id", name = "deviceId", example = "3")
    private Integer deviceId;

    /**
     * (外键，指向t_user的user_id字段)
     */
    @ApiModelProperty(value = "设备所属用户Id", name = "userId", example = "3", required = true)
    @NotNull(message = "设备所属用户Id不能为空")
    private Integer userId;

    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号", name = "deviceNumber", example = "318--03004", required = true)
    @NotNull(message = "设备编号不能为空")
    private String deviceNumber;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", name = "deviceName", example = "D80M-L-1-M-S", required = true)
    @NotNull(message = "设备名称不能为空")
    private String deviceName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "设备备注", name = "message", example = "该设备....")
    private String message;
}
