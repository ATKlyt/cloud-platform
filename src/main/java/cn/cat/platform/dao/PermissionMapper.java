package cn.cat.platform.dao;
import org.apache.ibatis.annotations.Param;

import cn.cat.platform.model.DO.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
  * @author linlt
  * @createTime 2020/4/3 11:25
  * @description TODO
  */
@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> findPermissionByUserId(Integer userId);

     List<Permission> findByPermissionUrl(@Param("permissionUrl")String permissionUrl);


}