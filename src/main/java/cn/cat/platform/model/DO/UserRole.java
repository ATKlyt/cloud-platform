package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author linlt
 * @createTime 2020/5/15 5:33
 * @description TODO
 */
@Data
public class UserRole implements Serializable {
    private Integer userId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;
}