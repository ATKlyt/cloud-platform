package cn.cat.platform.security.configuration;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.security.filter.JwtAuthenticationFilter;
import cn.cat.platform.security.handle.JwtAuthenticationFailureHandler;
import cn.cat.platform.security.handle.JwtAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

/**
 * @author linlt
 * @createTime 2020/4/5 14:14
 * @description TODO
 */

public class JwtAuthenticationConfigurer<T extends JwtAuthenticationConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {


    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public JwtAuthenticationConfigurer(JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                                       JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler) {
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter();
    }

    @Override
    public void configure(B builder) throws Exception {
        //允许不用认证就可以通过的路径
        permissiveRequestUrls(SecurityConstant.LOGIN_PATH, SecurityConstant.LOGOUT_PATH);

        jwtAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        //认证失败的处理逻辑
        jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        //认证成功的处理逻辑
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        //执行对象的后处理，将其变成一个bean
        JwtAuthenticationFilter authenticationFilter = postProcess(jwtAuthenticationFilter);
        //将filter放到logoutFilter之前
        builder.addFilterBefore(authenticationFilter, LogoutFilter.class);
    }

    public JwtAuthenticationConfigurer<T, B> permissiveRequestUrls(String ... urls){
        jwtAuthenticationFilter.setPermissiveUrl(urls);
        return this;
    }
}