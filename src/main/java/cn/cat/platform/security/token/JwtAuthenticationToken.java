package cn.cat.platform.security.token;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author linlt
 * @createTime 2020/4/5 13:31
 * @description TODO
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Claims claims;
    private String token;

    public JwtAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    public JwtAuthenticationToken(Claims claims, String token) {
        super(Collections.emptyList());
        this.claims = claims;
        this.token = token;
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Claims claims, String token) {
        super(authorities);
        this.claims = claims;
        this.token = token;
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    /**
     * 重要！重写setDetails(Object details)方法，并在里边将token的authenticated设置为true。
     * AuthenticationManager接收Authentication对象作为参数，
     * 并通过authenticate()方法对其进行验证(实际由其实现类ProviderManager完成)
     * ProviderManager会去轮询他的List<AuthenticationProvider> providers属性，
     * 里边存有我们自己定义的JwtAuthenticationProvider，轮询的过程中会逐一访问各个provider的support()方法，
     * 看support()方法是否支持对token进行更仔细的评估，
     * 如果支持，那么将会调用该provider的authenticate方法对token进行认证，
     * 如果result（即认证结果，result是由该provider返回的，
     * 在JwtAuthenticationProvider中返回的同样是JwtAuthenticationToken类的对象）不为null，
     * 将会调用copyDetails(authentication, result)方法，该方法会调用result的setDetails(Object details)方法，
     * 将result的details设置为authentication，即原来的token的details。
     * 在这个方法设置details的同时，我们也将result的身份认证设置为通过，
     * 如果result的身份认证没有设置为true，在之后的权限拦截中，
     * AbstractSecurityInterceptor类将会通过authentication的属性authenticated的值判断是否需要再次进行身份认证
     * 而没有进行设置肯定是需要再次认证，而此时的AbstractSecurityInterceptor的AuthenticationManager是NoOpAuthenticationManager
     * NoOpAuthenticationManager的authenticate()方法将会直接抛出一个AuthenticationServiceException认证服务异常，
     * 并由AuthenticationEntryPoint的commence()响应给客户端，
     * 而我们自定义了CustomizeAuthenticationEntryPoint实现了AuthenticationEntryPoint接口，
     * 所以具体的响应内容为用户未登录。
     * @param details
     */
    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
