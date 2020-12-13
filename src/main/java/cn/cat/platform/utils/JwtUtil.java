package cn.cat.platform.utils;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.enums.ResultCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * @author linlt
 * @createTime 2020/4/3 23:12
 * @description TODO
 */
public class JwtUtil {


    public static String generateToken(Collection<? extends GrantedAuthority> authorities, String username ) {

        StringBuilder authoritiesStr = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            authoritiesStr.append(authority.getAuthority()).append(" ");
        }

        return Jwts.builder()
                //用户权限
                .claim(SecurityConstant.AUTHORITIES, authoritiesStr)
                //创建时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //主体
                .setSubject(username)
                //token过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 20))
                //加密算法和密钥
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET_KEY.getBytes())
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstant.SECRET_KEY.getBytes())
                .parseClaimsJws(token.replace(SecurityConstant.TOKEN_PREFIX, "")).getBody();

    }

}
