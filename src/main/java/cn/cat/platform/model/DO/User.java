package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author linlt
 * @createTime 2020/12/1 17:16
 * @description TODO
 */
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    /**
    * 主键
    */
    private Integer userId;

    /**
    * 用户编号
    */
    @NotNull(message = "用户编号不能为空")
    private String userNumber;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotNull(message = "用户密码不能为空")
    private String password;

    /**
     * 客户名称
     */
    @NotNull(message = "客户名称不能为空")
    private String name;

    /**
    * 联系人
    */
    @NotNull(message = "联系人不能为空")
    private String contactPerson;

    /**
    * 联系方式
    */
    @NotNull(message = "联系方式不能为空")
    private String contactInfo;

    /**
     * 备注
     */
    @NotNull(message = "用户备注不能为空")
    private String message;

    /**
    * 地址
    */
    private String address;

    /**
    * 预留信息
    */
    private String reservedInfo;

    private static final long serialVersionUID = 1L;
}