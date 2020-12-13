package cn.cat.platform.service;

import cn.cat.platform.model.DO.UserRole;
    /**
 * @author linlt
 * @createTime 2020/5/15 5:33
 * @description TODO
 */
public interface UserRoleService{


    int insert(UserRole record);

    int insertSelective(UserRole record);

}
