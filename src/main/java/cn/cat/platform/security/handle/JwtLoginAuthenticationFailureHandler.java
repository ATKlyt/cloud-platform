package cn.cat.platform.security.handle;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linlt
 * @createTime 2020/4/3 14:03
 * @description 登录失败时的处理逻辑
 *
 * 登录失败处理器主要用来对登录失败的场景（密码错误、账号锁定等…）做统一处理并返回给前端统一的json返回体。
 * 在创建用户表的时候没有创建了账号过期、密码过期、账号锁定之类的字段，所以留作扩展点
 */
@Component
public class JwtLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //返回json数据
        Result result = null;
        if (exception instanceof AccountExpiredException) {
            //账号过期
            result = ResultUtil.error(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (exception instanceof BadCredentialsException) {
            //密码错误
            result = ResultUtil.error(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (exception instanceof CredentialsExpiredException) {
            //密码过期
            result = ResultUtil.error(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (exception instanceof DisabledException) {
            //账号不可用
            result = ResultUtil.error(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (exception instanceof LockedException) {
            //账号锁定
            result = ResultUtil.error(ResultCode.USER_ACCOUNT_LOCKED);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = ResultUtil.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else{
            //其他错误
            result = ResultUtil.error(ResultCode.COMMON_FAIL);
        }
        //处理编码方式，防止中文乱码的情况
        response.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
