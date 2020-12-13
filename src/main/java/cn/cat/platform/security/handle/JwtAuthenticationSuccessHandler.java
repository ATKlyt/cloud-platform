package cn.cat.platform.security.handle;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import cn.cat.platform.service.UserTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author linlt
 * @createTime 2020/4/5 13:53
 * @description TODO
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserTokenService userTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //下面是对token刷新的代码，暂时不开放这个功能
//        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
//        Claims claims = jwtAuthenticationToken.getClaims();
//        boolean shouldRefresh = shouldTokenRefresh(claims.getIssuedAt());
//        if(shouldRefresh) {
//            String newToken = userTokenService.refreshToken(jwtAuthenticationToken);
//            response.setHeader(Constant.TOKEN_HEADER, Constant.TOKEN_PREFIX + newToken);
//        }
    }

    /**
     * 检查token是否需要刷新，刷新间隔5分钟
     * @param issueAt
     * @return
     */
    protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(SecurityConstant.TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }
}
