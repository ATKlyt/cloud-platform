package cn.cat.platform.model.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/27 11:19
 * @description TODO
 */
@Data
public class UserVO {
    /**
     * 主键
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户编号
     */
    private String userNumber;

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

    private String roleName;

    /**
     * 在线状态
     */
    private String onlineStatus;

    /**
     * 地址
     */
    private String address;

    /**
     * 预留信息
     */
    private List<String> reservedInfoList;


    /**
     * 代理商id
     */
    private Integer superiorUserId;


}
