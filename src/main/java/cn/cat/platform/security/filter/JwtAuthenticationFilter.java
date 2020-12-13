package cn.cat.platform.security.filter;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/4 12:48
 * @description TODO
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private List<RequestMatcher> permissiveRequestMatchers;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationManager authenticationManager;

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // header没带token的，直接放过，因为部分url匿名用户也可以访问
        // 如果不支持匿名用户的请求没带token，这里放过也没问题，因为SecurityContext中没有认证信息，后面会被权限控制模块拦截
        if (!isRequestHaveAuthentication(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Authentication authentication = null;
        AuthenticationException failed = null;
        try {
            //从请求头中获取token
            String token = httpServletRequest.getHeader(SecurityConstant.TOKEN_HEADER);
            if (StringUtils.isEmpty(token)) {
                failed = new InsufficientAuthenticationException("token为空");
            } else {
                if (!token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                    failed = new InsufficientAuthenticationException("token前缀无效");
                } else {
                    //去除前缀
                    token = StringUtils.removeStart(token, SecurityConstant.TOKEN_PREFIX);
                    //将用户信息封装成jwtAuthenticationToken并交给authenticationManager
                    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
                    authentication = this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
                }
            }
        } catch (InternalAuthenticationServiceException e) {
            log.error("尝试对用户进行身份验证时发生内部错误", e);
            failed = e;
        } catch (AuthenticationException e) {
            //身份认证失败
            failed = e;
        }
        if (authentication != null) {
            //token认证成功
            successfulAuthentication(httpServletRequest, httpServletResponse, filterChain, authentication);
        } else if (!permissiveRequest(httpServletRequest)) {
            //token认证失败,并且这个request不在例外列表里，才会返回错误
            unsuccessfulAuthentication(httpServletRequest, httpServletResponse, failed);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }


    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected boolean permissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null)
            return false;
        for (RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if (permissiveMatcher.matches(request))
                return true;
        }
        return false;
    }

    /**
     * 判断请求头是否带有Authentication（即token）
     *
     * @return
     */
    protected boolean isRequestHaveAuthentication(HttpServletRequest httpServletRequest) {
        RequestHeaderRequestMatcher requestHeaderRequestMatcher =
                new RequestHeaderRequestMatcher(SecurityConstant.TOKEN_HEADER);
        return requestHeaderRequestMatcher.matches(httpServletRequest);
    }

    public void setAuthenticationSuccessHandler(
            AuthenticationSuccessHandler authenticationSuccessHandler) {
        Assert.notNull(authenticationSuccessHandler, "successHandler cannot be null");
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setAuthenticationFailureHandler(
            AuthenticationFailureHandler authenticationFailureHandler) {
        Assert.notNull(authenticationFailureHandler, "failureHandler cannot be null");
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    /**
     * 设置允许访问的连接
     *
     * @param urls
     */
    public void setPermissiveUrl(String... urls) {
        if (permissiveRequestMatchers == null) {
            permissiveRequestMatchers = new ArrayList<>();
        }
        for (String url : urls) {
            permissiveRequestMatchers.add(new AntPathRequestMatcher(url));
        }
    }
}
