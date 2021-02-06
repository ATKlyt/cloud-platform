package cn.cat.platform.dao;

import cn.cat.platform.model.BO.UserBO;import cn.cat.platform.model.DO.User;import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author linlt
 * @createTime 2020/12/1 17:16
 * @description TODO
 */
@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    List<User> findUserIdAndNameByRoleName(@Param("roleName") String roleName);

    List<Integer> findUserIdByRoleName(String roleName);

    List<UserBO> adminFindUserVO(@Param("userName") String userName,
                                 @Param("userNumber") String userNumber,
                                 @Param("roleName") String roleName);

    List<UserBO> findUserVOBySuperiorUserId(@Param("userName") String userName,
                                            @Param("userNumber") String userNumber,
                                            @Param("roleName") String roleName,
                                            @Param("loginUserId") Integer loginUserId);

    int insertSelective(User user);

    User selectByUserId(@Param("userId") Integer userId);

    int updatePasswordByUserId(@Param("newPassword") String newPassword, @Param("userId") Integer userId);

    int updateByUserId(@Param("user") User user);

    User findUserIdAndNameByUserId(@Param("userId") Integer userId);

    String selectPasswordByUserId(@Param("userId") Integer userId);

    User selectUserByUserNumber(String userNumber);

    int deleteUserByUserNumber(String userNumber);

    List<User> selectNotSuperior();

    List<User> selectUserInfoBySuperiorId(Integer userId);

    User selectUserinfoByUserId(Integer userId);



}