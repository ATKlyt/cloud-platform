package cn.cat.platform.security.handle;

import cn.cat.platform.model.DO.Permission;
import cn.cat.platform.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/3 20:17
 * @description 安全元数据源
 * <p>
 * 拦截当前的请求，并根据请求路径从数据库中查出当前资源路径需要哪些权限才能访问，
 * 然后将查出的需要的权限列表交给AccessDecisionManager去处理后续逻辑。
 * <p>
 * 需要先实现一个SecurityMetadataSource，翻译过来是"安全元数据源"，
 * 我们这里使用他的一个子类FilterInvocationSecurityMetadataSource。
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl().split("\\?")[0];

        //查询具体某个接口的权限
        List<Permission> permissionList = permissionService.findByPermissionUrl(requestUrl);
        if (permissionList == null || permissionList.size() == 0) {
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        //目前一个url对应一个权限，所以如果可以查到权限，size为1，可留做拓展点
        String[] attributes = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            attributes[i] = permissionList.get(i).getPermissionCode();
        }
        return SecurityConfig.createList(attributes);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
