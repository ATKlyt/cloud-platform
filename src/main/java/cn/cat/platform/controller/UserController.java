package cn.cat.platform.controller;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.model.param.UpdatePasswordParam;
import cn.cat.platform.model.param.UserParam;
import cn.cat.platform.model.DO.User;
import cn.cat.platform.model.VO.UserVO;
import cn.cat.platform.service.UserService;
import cn.cat.platform.utils.ResultUtil;
import cn.cat.platform.utils.ValidateUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author linlt
 * @CreateTime 2020/3/4 9:09
 */
@RestController
@RequestMapping("user")
@CrossOrigin
@Api(tags = "用户管理相关接口")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 查找普通用户的userId和姓名
     *
     * @return
     */
    @ApiOperation("查找普通用户列表（只包含id和姓名），用于添加设备时为设备指定所属用户")
    @GetMapping("findCommonUserBriefInfo")
    public Result findCommonUserBriefInfo() {
        List<User> users = userService.findCommonUserBriefInfo();
        return ResultUtil.success(users);
    }

    /**
     * 查找区域代理商的userId和姓名
     *
     * @return
     */
    @ApiOperation("查找区域代理商列表（只包含id和姓名），用于添加用户时为用户指定所属区域代理商")
    @GetMapping("findRegionalAgentBriefInfo")
    public Result findRegionalAgentBriefInfo() {
        List<User> users = userService.findRegionalAgentBriefInfo();
        return ResultUtil.success(users);
    }

    /**
     * 根据登录用户查找用户
     *
     * @param pn          页数
     * @param userName    查找条件：用户真实姓名
     * @param userNumber  查找条件：用户编号
     * @param roleName    查找条件：角色
     * @param loginUserId 登录用户id
     * @return
     */
    @ApiOperation("根据登录用户（厂家管理员或是区域代理商）查找用户列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pn", value = "页数", example = "1", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "userName", value = "查询条件:用户真实姓名", example = "林超师", dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "userNumber", value = "查询条件:用户编号", example = "900000", dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "roleName", value = "查询条件:角色", example = "用户", dataType = "string", paramType = "query"),
                    @ApiImplicitParam(name = "loginUserId", value = "当前登录的用户Id", example = "17", required = true, dataType = "int", paramType = "query")
            }
    )
    @GetMapping("findUser")
    public Result findUser(@RequestParam(name = "pn", defaultValue = "1") Integer pn,
                           @RequestParam(name = "userName", defaultValue = "") String userName,
                           @RequestParam(name = "userNumber", defaultValue = "") String userNumber,
                           @RequestParam(name = "roleName", defaultValue = "") String roleName,
                           @RequestParam(name = "loginUserId") Integer loginUserId) {
        PageInfo<UserVO> pageInfo = userService.findUser(pn, loginUserId, userName, userNumber, roleName);
        System.out.println(pageInfo);
        return ResultUtil.success(pageInfo);
    }


    /**
     * 添加用户
     *
     * @param userParam
     * @param bindingResult
     * @return
     */
    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public Result addUser(@RequestBody @Validated UserParam userParam, BindingResult bindingResult) {
        ValidateUtil.validateParam(bindingResult);
        if (!userParam.getConfirmPassword().equals(userParam.getPassword())) {
            return ResultUtil.error(ResultCode.TWO_PASSWORDS_INCONSISTENT);
        }
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        StringBuilder reservedInfo = null;
        List<String> reservedInfoList = userParam.getReservedInfoList();
        if (reservedInfoList != null) {
            reservedInfo = new StringBuilder();
            for (String info : reservedInfoList) {
                reservedInfo.append(info).append(Constant.delimiter);
            }
        }
        user.setReservedInfo(reservedInfo == null ? null : reservedInfo.toString());
        Integer roleId = userParam.getRoleId();
        Integer superiorUserId = userParam.getSuperiorUserId();
        if (Constant.ROLE_ID_USER.equals(roleId)) {
            ValidateUtil.validateParams(superiorUserId);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // 添加用户
        UserVO resultUser = userService.addUser(user, roleId, superiorUserId);

        return ResultUtil.success(resultUser);
    }

    /**
     * 检查数据库是否已有该用户名
     *
     * @param username 用户名
     * @return
     */
    @ApiOperation("检查用户名（username）是否已存在")
    @ApiImplicitParam(name = "username", value = "用户名", example = "Colin", required = true, dataType = "string", paramType = "query")
    @GetMapping("checkUser")
    public Result checkUser(String username) {
        //验证用户名是否已经存在
        boolean isExist = userService.checkUserByUsername(username);
        if (isExist) {
            return ResultUtil.error(ResultCode.USERNAME_IS_EXIST);
        }
        return ResultUtil.success();
    }


    @ApiOperation("修改密码")
    @PostMapping("updatePassword")
    public Result updatePassword(@RequestBody @Validated UpdatePasswordParam updatePasswordParam, BindingResult bindingResult) {
        ValidateUtil.validateParam(bindingResult);
        if (!updatePasswordParam.getNewPassword().equals(updatePasswordParam.getConfirmPassword())) {
            return ResultUtil.error(ResultCode.TWO_PASSWORDS_INCONSISTENT);
        }
        boolean isSuccess = userService.updatePassword(updatePasswordParam);
        if (!isSuccess) {
            return ResultUtil.error(ResultCode.OLD_PASSWORD_IS_WRONG);
        }
        return ResultUtil.success();
    }

    @ApiOperation("修改用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "用户id", name = "userId", example = "900000", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(value = "用户编号", name = "userNumber", example = "900000", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "用户名称", name = "username", example = "colin", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "用户真实姓名", name = "name", example = "林超师", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "用户联系", name = "contact", example = "18666555555", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "用户备注", name = "message", example = "该用户....", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "所属区域代理商用户Id", name = "superiorUserId", example = "17", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "地址信息", name = "superiorUserId", example = "广州", required = true, dataType = "string", paramType = "query"),
                    @ApiImplicitParam(value = "预留信息", name = "reservedInfoList", example = "[\"1\",\"2\",\"3\"]", required = true, dataType = "string", paramType = "query")
            }
    )
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        StringBuilder reservedInfo = null;
        List<String> reservedInfoList = userParam.getReservedInfoList();
        if (reservedInfoList != null) {
            reservedInfo = new StringBuilder();
            for (String info : reservedInfoList) {
                reservedInfo.append(info).append(Constant.delimiter);
            }
        }
        user.setReservedInfo(reservedInfo == null ? null : reservedInfo.toString());
        userService.updateUserInfo(user);
        return ResultUtil.success(userParam);
    }

    @ApiOperation("判断用户编号是否存在")
    @PostMapping("checkUserNumber")
    public Result checkUserNumber(@RequestBody User user){
        System.out.println(2222);
        //验证用户编号是否已经存在
        boolean isExist = userService.checkUserNumber(user.getUserNumber());
        if (isExist) {
            return ResultUtil.error(ResultCode.USERNUMBER_IS_EXIT);
        }
        return ResultUtil.success();
    }


    @ApiOperation("删除用户")
    @DeleteMapping("deleteUserByUserNumber")
    public Result deleteUserByUserNumber(@RequestBody User user){
        System.out.println(233);
        //验证用户是否删除成功
        int i = userService.deleteUserByUserNumber(user.getUserNumber());
        if (0 == i) {
            return ResultUtil.error(ResultCode.DELETE_FAIL);
        }
        return ResultUtil.success();
    }

    @ApiOperation("查找出未指定代理商的普通用户")
    @GetMapping("selectNotSuperior")
    public Result selectNotSuperior(){
        List<User> users = userService.selectNotSuperior();
        return ResultUtil.success(users,"成功");
    }


}
