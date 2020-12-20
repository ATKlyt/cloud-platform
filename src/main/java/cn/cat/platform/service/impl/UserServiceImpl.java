package cn.cat.platform.service.impl;

import cn.cat.platform.common.Constant;
import cn.cat.platform.dao.UserManagementMapper;
import cn.cat.platform.dao.UserRoleMapper;
import cn.cat.platform.dao.UserTokenMapper;
import cn.cat.platform.model.BO.UserBO;
import cn.cat.platform.model.DO.UserManagement;
import cn.cat.platform.model.DO.UserRole;
import cn.cat.platform.model.DO.UserToken;
import cn.cat.platform.model.VO.UserVO;
import cn.cat.platform.model.param.UpdatePasswordParam;
import cn.cat.platform.service.UserService;
import cn.cat.platform.utils.JwtUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.cat.platform.model.DO.User;
import cn.cat.platform.dao.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author linlt
 * @CreateTime 2020/3/4 4:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserTokenMapper userTokenMapper;
    @Resource
    private UserManagementMapper userManagementMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> findCommonUserBriefInfo() {
        return userMapper.findUserIdAndNameByRoleName(Constant.ROLE_USER);
    }

    @Override
    public PageInfo<UserVO> findUser(Integer pn, Integer loginUserId, String userName, String userNumber, String roleName) {
        List<UserBO> userBOS = new ArrayList<>(0);
        // 登录用户的角色id
        Integer loginUserRoleId = userRoleMapper.findRoleIdByUserId(loginUserId);
        if (loginUserRoleId.equals(Constant.ROLE_ID_ADMIN)) {
            // 厂家管理员
            PageHelper.startPage(pn, Constant.PAGE_SIZE);
            userBOS = userMapper.adminFindUserVO(userName, userNumber, roleName);
        } else if (loginUserRoleId.equals(Constant.ROLE_ID_REGIONAL_AGENT)) {
            // 区域代理啥
            PageHelper.startPage(pn, Constant.PAGE_SIZE);
            userBOS = userMapper.findUserVOBySuperiorUserId(userName, userNumber, roleName, loginUserId);
        }
        List<UserVO> userVOS = new ArrayList<>(userBOS.size());
        for (UserBO userBO : userBOS) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userBO, userVO);
            //根据Uid生成token
            UserToken userToken = userTokenMapper.findByUserId(userVO.getUserId());

            userVO.setOnlineStatus(getOnlineStatus(userToken));
            String reservedInfoStr = userBO.getReservedInfo();
            if (reservedInfoStr != null) {
                userVO.setReservedInfoList(Arrays.asList(reservedInfoStr.split(Constant.delimiter)));
            } else {
                userVO.setReservedInfoList(new ArrayList<>(0));
            }
            userVOS.add(userVO);
        }
        PageInfo<UserBO> userBOPageInfo = new PageInfo<>(userBOS);
        PageInfo<UserVO> userVOPageInfo = new PageInfo<>(userVOS);
        userVOPageInfo.setTotal(userBOPageInfo.getTotal());
        return userVOPageInfo;
    }

    private String getOnlineStatus(UserToken userToken) {
        //根据token判断登录状态是否过期(是否在线)
        String onlineStatus;
        if (userToken == null) {
            // 没有token
            onlineStatus = Constant.NOT_ONLINE_STATUS;
        } else {
            try {
                //先设置成在线状态
                onlineStatus = Constant.ONLINE_STATUS;
                JwtUtil.parseToken(userToken.getToken());
            } catch (ExpiredJwtException e) {
                //token过期，解析失败
                onlineStatus = Constant.NOT_ONLINE_STATUS;
            }
        }
        return onlineStatus;
    }


    @Override
    public boolean checkUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean checkUserNumber(String userNumber) {
        User user = userMapper.selectUserByUserNumber(userNumber);
        return user != null;
    }

    @Override
    public UserVO addUser(User user, Integer roleId, Integer superiorUserId) {
        // 添加用户信息
        userMapper.insertSelective(user);
        // 添加角色信息
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(roleId);
        userRoleMapper.insertSelective(userRole);
        if (Constant.ROLE_ID_USER.equals(roleId)) {
            //添加普通用户，指定上级区域代理商
            UserManagement userManagement = new UserManagement();
            userManagement.setSuperiorUserId(superiorUserId);
            userManagement.setUserId(user.getUserId());
            userManagementMapper.insert(userManagement);
        }
        String reservedInfo = user.getReservedInfo();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        List<String> reservedInfoList = new ArrayList<>(0);
        if (reservedInfo != null) {
            reservedInfoList = Arrays.asList(reservedInfo.split(Constant.delimiter));
        }
        userVO.setReservedInfoList(reservedInfoList);
        return userVO;
    }

    @Override
    public List<User> findRegionalAgentBriefInfo() {
        return userMapper.findUserIdAndNameByRoleName(Constant.ROLE_REGIONAL_AGENT);
    }

    @Override
    public boolean updatePassword(UpdatePasswordParam updatePasswordParam) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        updatePasswordParam.setNewPassword(bCryptPasswordEncoder.encode(updatePasswordParam.getNewPassword()));
        String nowPassword = userMapper.selectPasswordByUserId(updatePasswordParam.getUserId());
        boolean matches = bCryptPasswordEncoder.matches(updatePasswordParam.getOldPassword(), nowPassword);
        if (!matches) {
            return false;
        }
        userMapper.updatePasswordByUserId(updatePasswordParam.getNewPassword(),
                updatePasswordParam.getUserId());
        return true;
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateByUserId(user);
    }

    @Override
    public User findSuperiorUserByUserId(Integer userId) {
        UserManagement userManagement = userManagementMapper.selectByUserId(userId);
        if (userManagement == null) {
            return null;
        } else {
            return userMapper.findUserIdAndNameByUserId(userManagement.getSuperiorUserId());
        }

    }

    @Override
    public int deleteUserByUserNumber(String userNumber){
        return userMapper.deleteUserByUserNumber(userNumber);
    }

    @Override
    public List<User> selectNotSuperior(){
        List<User> users = userMapper.selectNotSuperior();
        return users;
    }


}






