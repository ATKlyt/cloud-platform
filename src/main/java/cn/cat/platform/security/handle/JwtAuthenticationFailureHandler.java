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
 * @createTime 2020/4/5 13:52
 * @description TODO
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //返回json数据
        Result result = ResultUtil.error(ResultCode.TOKEN_INVALID, exception.getMessage());
        //处理编码方式，防止中文乱码的情况
        response.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }

}
