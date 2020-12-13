package cn.cat.platform.model.DO;

import java.io.Serializable;
import lombok.Data;

/**
  * @author linlt
  * @createTime 2020/4/3 11:25
  * @description TODO
  */
@Data
public class Permission implements Serializable {
    private Integer permissionId;

    private String permissionCode;

    private String permissionDescription;

    private String permissionUrl;

    private static final long serialVersionUID = 1L;
}