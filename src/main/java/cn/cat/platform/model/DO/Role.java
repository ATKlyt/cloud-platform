package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;

/**
  * @author linlt
  * @createTime 2020/4/3 11:10
  * @description TODO
  */
@Data
public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleDescription;

    private static final long serialVersionUID = 1L;

}