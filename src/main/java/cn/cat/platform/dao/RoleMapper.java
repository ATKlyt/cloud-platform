package cn.cat.platform.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.cat.platform.model.DO.Role;
import org.apache.ibatis.annotations.Mapper;

/**
  * @author linlt
  * @createTime 2020/4/3 11:10
  * @description TODO
  */
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);


    Role findByRoleName(@Param("roleName")String roleName);

    List<Role> findAll();


    Role findByUserId(Integer userId);
}