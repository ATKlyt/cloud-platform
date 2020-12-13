package cn.cat.platform.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.cat.platform.model.DO.UserManagement;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author linlt
 * @createTime 2020/7/10 6:08
 * @description TODO
 */
@Mapper
public interface UserManagementMapper {
    int insert(UserManagement record);

    int insertSelective(UserManagement record);

    UserManagement selectByUserId(@Param("userId")Integer userId);


}