package cn.cat.platform.security.provide;

import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.model.DO.User;
import cn.cat.platform.model.DO.UserToken;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import cn.cat.platform.service.PermissionService;
import cn.cat.platform.service.UserService;
import cn.cat.platform.service.UserTokenService;
import cn.cat.platform.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/5 11:53
 * @description TODO
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    public JwtAuthenticationProvider(UserTokenService userTokenService, UserService userService, PermissionService permissionService) {
        this.userTokenService = userTokenService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((JwtAuthenticationToken) authentication).getToken();
        Claims claims;
        try {
            //对token解码
            claims = JwtUtil.parseToken(token);
        } catch (SignatureException e) {
            //token无效
            throw new BadCredentialsException("token解码失败");
        } catch (ExpiredJwtException e){
            //当token有效期到了之后解码会失败
            throw new NonceExpiredException("Token已过期，请重新登录");
        }
        //判断token是否过期
        if (claims.getExpiration().before(Calendar.getInstance().getTime())) {
            //token有效期到了（好像执行不到这里）
            throw new NonceExpiredException("Token已过期");
        }
        //从token中获取的username
        String username = claims.getSubject();
        //从数据库中查询token是否过期
        User user = userService.findByUsername(username);
        UserToken userToken = userTokenService.getToken(user.getUserId());
        if (userToken == null || !userToken.getToken().equals(token)) {
            throw new NonceExpiredException("Token已过期，请重新登录");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //从token里边获取用户权限
        String[] authorities = ((String) claims.get(SecurityConstant.AUTHORITIES)).split(" ");
        for (String authority : authorities) {
            if (!authority.isEmpty()){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
                grantedAuthorities.add(grantedAuthority);
            }
        }

        return new JwtAuthenticationToken(grantedAuthorities,claims,token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
