package cn.cat.platform.service;

import cn.cat.platform.model.DO.Permission;

import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/3 11:25
 * @description TODO
 */
public interface PermissionService {


    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> findByPermissionUrl(String permissionUrl);

    List<Permission> findPermissionByUserId(Integer userId);
}
