package cn.cat.platform.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @createTime 2020/11/22 21:30
 * @description TODO
 */
@ApiModel
@Data
public class UserQuery {
    @ApiModelProperty(value = "用户联系", name = "contact", example = "18666555555", required = true)
    @NotNull(message = "用户联系方式不能为空")
    private Integer pn;
    @ApiModelProperty(value = "用户联系", name = "contact", example = "18666555555", required = true)
    @NotNull(message = "用户联系方式不能为空")
    private String userName;
    @ApiModelProperty(value = "用户联系", name = "contact", example = "18666555555", required = true)
    @NotNull(message = "用户联系方式不能为空")
    private String userNumber;
    @ApiModelProperty(value = "用户联系", name = "contact", example = "18666555555", required = true)
    @NotNull(message = "用户联系方式不能为空")
    private String roleName;
    @ApiModelProperty(value = "登录用户Id", name = "loginUserId", example = "1", required = true)
    @NotNull(message = "登录用户Id不能为空")
    private Integer loginUserId;
}
