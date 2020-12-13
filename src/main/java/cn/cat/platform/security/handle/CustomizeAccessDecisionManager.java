package cn.cat.platform.security.handle;

import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author linlt
 * @createTime 2020/4/3 20:18
 * @description 访问决策管理器
 *
 * 实现基于JDBC的动态权限控制
 * 需要实现一个AccessDecisionManager（访问决策管理器），
 * 在里面我们对当前请求的资源进行权限判断，判断当前登录用户是否拥有该权限，
 * 如果有就放行，如果没有就抛出一个"权限不足"的异常。
 */
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            //当前请求需要的权限
            String attribute = configAttribute.getAttribute();
            //当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(attribute)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
