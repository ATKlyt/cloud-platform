package cn.cat.platform.service;

import cn.cat.platform.model.DO.Role;

import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/3 11:10
 * @description TODO
 */
public interface RoleService {


    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Role findByUserId(Integer userId);

    List<Role> findRole();
}
