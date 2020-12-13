package cn.cat.platform.security.handle;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linlt
 * @createTime 2020/4/3 11:59
 * @description 匿名用户访问无权限资源返回结果
 *
 * 前后端分离的情况下，我们需要的是返回给前端"用户未登录"的提示信息，
 * 所以我们需要做的就是屏蔽重定向的登录页面，并返回统一的json格式的返回体。
 * 而实现这一功能的核心就是实现AuthenticationEntryPoint并在WebSecurityConfig中注入，
 * 然后在configure(HttpSecurity http)方法中。
 * AuthenticationEntryPoint主要是用来处理匿名用户访问无权限资源时的异常（即未登录，或者登录状态过期失效）
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = ResultUtil.error(ResultCode.USER_NOT_LOGIN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
