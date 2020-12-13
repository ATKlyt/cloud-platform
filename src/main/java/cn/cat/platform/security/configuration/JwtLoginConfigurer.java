package cn.cat.platform.security.configuration;

import cn.cat.platform.security.filter.JwtLoginFilter;
import cn.cat.platform.security.handle.JwtLoginAuthenticationFailureHandler;
import cn.cat.platform.security.handle.JwtLoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.stereotype.Component;

/**
 * @author linlt
 * @createTime 2020/4/5 12:03
 * @description TODO
 */

public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {


    private JwtLoginAuthenticationSuccessHandler jwtLoginAuthenticationSuccessHandler;

    private JwtLoginAuthenticationFailureHandler jwtLoginAuthenticationFailureHandler;

    private JwtLoginFilter jwtLoginFilter;

    public JwtLoginConfigurer(JwtLoginAuthenticationSuccessHandler jwtLoginAuthenticationSuccessHandler,
                              JwtLoginAuthenticationFailureHandler jwtLoginAuthenticationFailureHandler) {
        this.jwtLoginAuthenticationSuccessHandler = jwtLoginAuthenticationSuccessHandler;
        this.jwtLoginAuthenticationFailureHandler = jwtLoginAuthenticationFailureHandler;
        this.jwtLoginFilter = new JwtLoginFilter();
    }

    @Override
    public void configure(B builder) throws Exception {
        jwtLoginFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        //登录成功时的处理逻辑
        jwtLoginFilter.setAuthenticationSuccessHandler(jwtLoginAuthenticationSuccessHandler);
        //登录失败时的处理逻辑
        jwtLoginFilter.setAuthenticationFailureHandler(jwtLoginAuthenticationFailureHandler);
        //不将认证后的context放入session
        jwtLoginFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        //执行对象的后处理，将其变成一个bean
        JwtLoginFilter loginFilter = postProcess(jwtLoginFilter);
        //将filter放到logoutFilter之后
        builder.addFilterAfter(loginFilter, LogoutFilter.class);
    }


}
