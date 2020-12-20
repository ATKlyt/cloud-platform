package cn.cat.platform.service;

import cn.cat.platform.model.BO.UserBO;
import cn.cat.platform.model.DO.User;
import cn.cat.platform.model.VO.UserVO;
import cn.cat.platform.model.param.UpdatePasswordParam;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author linlt
 * @CreateTime 2020/3/4 9:03
 */
public interface UserService {
    User findByUsername(String username);


    /**
     * 查找普通用户的userId和姓名
     *
     * @return
     */
    List<User> findCommonUserBriefInfo();

    /**
     * 查询所有用户（编号、姓名、联系方式、备注、角色）
     *
     * @param pn
     * @param userName
     * @param userNumber
     * @param roleName
     * @return
     */
    PageInfo<UserVO> findUser(Integer pn, Integer loginUserId, String userName, String userNumber, String roleName);

    boolean checkUserByUsername(String username);

    boolean checkUserNumber(String userNumber);

    UserVO addUser(User user, Integer roleId, Integer superiorUserId);

    List<User> findRegionalAgentBriefInfo();

    boolean updatePassword(UpdatePasswordParam updatePasswordParam);

    int updateUserInfo(User user);

    User findSuperiorUserByUserId(Integer userId);

    int deleteUserByUserNumber(String userNumber);

    /**
     * @Date         2020/12/20 14:51
     * @Author       吴东龙
     * @Description  查询没有指定区域商的用户
     */
    List<User> selectNotSuperior();


}






