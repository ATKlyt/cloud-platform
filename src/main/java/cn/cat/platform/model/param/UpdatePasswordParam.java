package cn.cat.platform.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @createTime 2020/12/1 12:51
 * @description TODO
 */
@Data
@ApiModel
public class UpdatePasswordParam {
    @ApiModelProperty(value = "用户id", name = "userId", example = "900000", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiModelProperty(value = "旧密码", name = "oldPassword", example = "111111", required = true)
    @NotNull(message = "旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", name = "newPassword", example = "123456", required = true)
    @NotNull(message = "新密码不能为空")
    private String newPassword;
    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = true)
    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;
}
