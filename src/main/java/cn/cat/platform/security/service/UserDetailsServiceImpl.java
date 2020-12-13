package cn.cat.platform.security.service;

import cn.cat.platform.dao.PermissionMapper;
import cn.cat.platform.dao.UserMapper;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.model.DO.Permission;
import cn.cat.platform.model.DO.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/3 10:51
 * @description TODO
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;
    @Resource
    PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new BusinessException(ResultCode.LOGIN_USERNAME_IS_NULL);
        }
        //根据用户名查询用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        //获取该用户所拥有的权限
        List<Permission> permissions = permissionMapper.findPermissionByUserId(user.getUserId());
        // 声明用户授权
        for (Permission permission : permissions) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionCode());
            grantedAuthorities.add(grantedAuthority);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities).build();

    }
}
