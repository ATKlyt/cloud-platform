package cn.cat.platform.service;

import cn.cat.platform.model.DO.UserManagement;
    /**
 * @author linlt
 * @createTime 2020/7/10 6:08
 * @description TODO
 */
public interface UserManagementService{


    int insert(UserManagement record);

    int insertSelective(UserManagement record);

}
