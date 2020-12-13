package cn.cat.platform.security.filter;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.SecurityConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * @author linlt
 * @createTime 2020/4/3 23:41
 * @description 登录时的认证
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter() {
        super(new AntPathRequestMatcher(SecurityConstant.LOGIN_PATH, SecurityConstant.METHOD_POST));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String username = null, password = null;
        if(StringUtils.hasText(body)) {
            JSONObject jsonObject = JSON.parseObject(body);
            username = jsonObject.getString("username");
            password = jsonObject.getString("password");
        }

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        //封装到usernamePasswordAuthenticationToken中提交
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

}