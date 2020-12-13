package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author linlt
 * @createTime 2020/7/10 6:08
 * @description TODO
 */
@Data
public class UserManagement implements Serializable {
    private Integer userId;

    private Integer superiorUserId;

    private static final long serialVersionUID = 1L;
}