package cn.cat.platform.dao;
import org.apache.ibatis.annotations.Param;

import cn.cat.platform.model.DO.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author linlt
 * @createTime 2020/5/15 5:33
 * @description TODO
 */
@Mapper
public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

     Integer findRoleIdByUserId(@Param("userId")Integer userId);


}