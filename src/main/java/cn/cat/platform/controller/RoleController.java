package cn.cat.platform.controller;

import cn.cat.platform.common.Result;
import cn.cat.platform.model.DO.Role;
import cn.cat.platform.service.RoleService;
import cn.cat.platform.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author linlt
 * @createTime 2020/7/20 1:05
 * @description TODO
 */
@RestController
@RequestMapping("role")
@CrossOrigin
@Api(tags = "角色管理相关接口")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 查找所有角色
     *
     * @return
     */
    @ApiOperation("查找所有角色（除了管理员）")
    @GetMapping("findRole")
    public Result findRole() {
        List<Role> roles = roleService.findRole();
        return ResultUtil.success(roles);
    }
}
