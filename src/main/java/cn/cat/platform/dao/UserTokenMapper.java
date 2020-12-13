package cn.cat.platform.dao;

import cn.cat.platform.model.DO.UserToken;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

/**
 * @author linlt
 * @createTime 2020/4/17 14:00
 * @description TODO
 */
@Mapper
public interface UserTokenMapper {
    int deleteByPrimaryKey(Integer tokenId);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Integer tokenId);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);

    int deleteByUserId(@Param("userId") Integer userId);

    int updateByUserId(UserToken userToken);

    UserToken findByUserId(@Param("userId") Integer userId);

    UserToken findByToken(@Param("token") String token);
}