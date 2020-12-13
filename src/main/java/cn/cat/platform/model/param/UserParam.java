package cn.cat.platform.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/8/2 11:36
 * @description TODO
 */
@ApiModel
@Data
public class UserParam {
    @ApiModelProperty(value = "用户id", name = "userId", example = "900000", required = true)
    private Integer userId;
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", name = "userNumber", example = "900000", required = true)
    @NotNull(message = "用户编号不能为空")
    private String userNumber;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", name = "username", example = "colin", required = true)
    @NotNull(message = "用户名称不能为空")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", name = "password", example = "123456", required = true)
    @NotNull(message = "用户密码不能为空")
    private String password;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户确认密码", name = "confirmPassword", example = "123456", required = true)
    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称", name = "name", example = "广工工业大学", required = true)
    @NotNull(message = "客户名称不能为空")
    private String name;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "用户联系", name = "contactInfo", example = "18666555555", required = true)
    @NotNull(message = "联系方式不能为空")
    private String contactInfo;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "用户联系", name = "contactPerson", example = "林乐涛", required = true)
    @NotNull(message = "联系人不能为空")
    private String contactPerson;

    /**
     * 备注
     */
    @ApiModelProperty(value = "用户备注", name = "message", example = "该用户....", required = true)
    @NotNull(message = "用户备注不能为空")
    private String message;

    /**
     * 备注
     */
    @ApiModelProperty(value = "新用户角色Id", name = "roleId", example = "2", required = true)
    @NotNull(message = "用户角色不能为空")
    private Integer roleId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "所属区域代理商用户Id", name = "superiorUserId", example = "17")
    private Integer superiorUserId;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址信息", name = "superiorUserId", example = "广州")
    @NotNull(message = "地址不能为空")
    private String address;

    /**
     * 预留信息
     */
    @ApiModelProperty(value = "预留信息", name = "reservedInfoList", example = "[\"1\",\"2\",\"3\"]")
    private List<String> reservedInfoList;

}
