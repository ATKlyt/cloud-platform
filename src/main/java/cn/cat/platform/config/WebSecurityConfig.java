package cn.cat.platform.config;

import cn.cat.platform.security.configuration.JwtAuthenticationConfigurer;
import cn.cat.platform.security.provide.JwtAuthenticationProvider;
import cn.cat.platform.security.configuration.JwtLoginConfigurer;
import cn.cat.platform.security.handle.*;
import cn.cat.platform.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author linlt
 * @createTime 2020/4/3 9:53
 * @description TODO
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 匿名用户访问无权限资源时的异常
     */
    @Autowired
    CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    /**
     * 登出成功时的处理逻辑
     */
    @Autowired
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    /**
     * 权限不足处理逻辑
     */
    @Autowired
    CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

    /**
     * 访问决策管理器
     */
    @Autowired
    CustomizeAccessDecisionManager customizeAccessDecisionManager;

    /**
     * 安全元数据源
     */
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    /**
     * jwt登录成功后的处理逻辑
     */
    @Autowired
    private JwtLoginAuthenticationSuccessHandler jwtLoginAuthenticationSuccessHandler;
    /**
     * jwt登录失败后的处理逻辑
     */
    @Autowired
    private JwtLoginAuthenticationFailureHandler jwtLoginAuthenticationFailureHandler;
    /**
     * jwt认证通过后的处理逻辑
     */
    @Autowired
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    /**
     * jwt认证失败后的处理逻辑
     */
    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    /**
     * jwt认证的处理逻辑
     */
    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;


    @Bean
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception {
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置provider
        auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(jwtAuthenticationProvider);
    }

    /**
     * 要添加@Bean，自定义UserDetailsServerImpl里边的mapper才会自动注入，
     * 否则就算是在自定义UserDetailsServerImpl加上@Service也没用
     * Spring Security在Spring加载完Bean之前就加载了
     *
     * @return
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * 对提交的密码进行加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    /**
     * 不对静态资源拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/index.html");
        // swagger资源
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/v2/api-docs");
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“,”分隔
        configuration.setAllowedOrigins(Arrays.asList("*"));
        //允许的请求方法，POST、GET等
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTION", "PUT", "DELETE"));
        //header，允许哪些header
        configuration.setAllowedHeaders(Arrays.asList("*"));
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启允许iframe 嵌套
        http.headers().frameOptions().disable();
        http.csrf().disable()
                // 开启跨域
                .cors();
        //http相关配置，包括登入登出、异常处理、会话管理等
        http.authorizeRequests()
                //权限认证，将自定义访问决策管理器和自定义安全元数据注入进来
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        //决策管理器
                        object.setAccessDecisionManager(customizeAccessDecisionManager);
                        //安全元数据源
                        object.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                //配置jwt认证授权
                .and()
                .apply((new JwtAuthenticationConfigurer<>(
                        jwtAuthenticationSuccessHandler, jwtAuthenticationFailureHandler)))
                .and()
                //配置jwt登录
                .apply(new JwtLoginConfigurer<>(
                        jwtLoginAuthenticationSuccessHandler, jwtLoginAuthenticationFailureHandler))


                //登出，默认登入路径为/logout，方法为GET
                .and().logout()
                //允许所有用户
                .permitAll()
                //登出成功处理逻辑
                .logoutSuccessHandler(customizeLogoutSuccessHandler)
                //登出之后删除cookie
                .deleteCookies("JSESSIONID")

                //异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling()
                //匿名用户访问无权限资源时的异常处理（未登录/登陆失效）
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                //用户访问无权限资源时的异常处理（权限不足）
                .accessDeniedHandler(customizeAccessDeniedHandler)

                .and()
                //使用session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
