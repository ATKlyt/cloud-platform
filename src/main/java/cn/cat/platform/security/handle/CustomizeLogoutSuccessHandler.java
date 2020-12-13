package cn.cat.platform.security.handle;

import cn.cat.platform.common.Result;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import cn.cat.platform.service.UserTokenService;
import cn.cat.platform.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linlt
 * @createTime 2020/4/3 14:11
 * @description 登出成功时的处理逻辑
 * <p>
 * 登出之后会将token清除
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserTokenService userTokenService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            String username = (String)
                    ((JwtAuthenticationToken) authentication).getClaims().get("sub");
            if (username != null){
                userTokenService.clearToken(username);
            }
        }

        Result result = ResultUtil.success();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
