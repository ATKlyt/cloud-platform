package cn.cat.platform.model.BO;

import lombok.Data;

/**
 * @author linlt
 * @createTime 2020/11/22 21:08
 * @description TODO
 */
@Data
public class UserBO {
    /**
     * 主键
     */
    private Integer userId;
    /**
     * 用户编号
     */
    private String userNumber;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 备注
     */
    private String message;

    /**
     * 地址
     */
    private String address;

    /**
     * 预留信息
     */
    private String reservedInfo;

    private String roleName;

    /**
     * 代理商id
     */
    private Integer superiorUserId;
}
