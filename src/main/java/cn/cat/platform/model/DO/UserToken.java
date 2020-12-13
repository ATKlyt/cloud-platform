package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author linlt
 * @createTime 2020/4/17 14:00
 * @description TODO
 */
@Data
public class UserToken implements Serializable {
    private Integer tokenId;

    private Integer userId;

    private String token;

    private static final long serialVersionUID = 1L;
}