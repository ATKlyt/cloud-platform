package cn.cat.platform.service.impl;

import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.dao.UserMapper;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import cn.cat.platform.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.cat.platform.dao.UserTokenMapper;
import cn.cat.platform.model.DO.UserToken;
import cn.cat.platform.service.UserTokenService;

import java.util.Collection;
import java.util.List;

/**
 * @author linlt
 * @createTime 2020/4/5 15:04
 * @description TODO
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private UserTokenMapper userTokenMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer tokenId) {
        return userTokenMapper.deleteByPrimaryKey(tokenId);
    }

    @Override
    public int insert(UserToken record) {
        return userTokenMapper.insert(record);
    }

    @Override
    public int insertSelective(UserToken record) {
        return userTokenMapper.insertSelective(record);
    }

    @Override
    public UserToken selectByPrimaryKey(Integer tokenId) {
        return userTokenMapper.selectByPrimaryKey(tokenId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserToken record) {
        return userTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserToken record) {
        return userTokenMapper.updateByPrimaryKey(record);
    }

    @Override
    public String saveToken(User userDetails, Integer userId) {
        //用户名
        String username = userDetails.getUsername();
        //用户权限
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String token = JwtUtil.generateToken(authorities, username);
        UserToken newUserToken = new UserToken();
        newUserToken.setUserId(userId);
        newUserToken.setToken(token);
        UserToken oldUserToken = userTokenMapper.findByUserId(userId);
        if (oldUserToken == null) {
            insertSelective(newUserToken);
        } else {
            userTokenMapper.updateByUserId(newUserToken);
        }
        return token;
    }

    @Override
    public UserToken getToken(Integer userId) {
        return userTokenMapper.findByUserId(userId);
    }

    @Override
    public void clearToken(String username) {
        cn.cat.platform.model.DO.User user = userMapper.findByUsername(username);
        userTokenMapper.deleteByUserId(user.getUserId());
    }

    @Override
    public String refreshToken(JwtAuthenticationToken jwtAuthenticationToken) {
        Claims claims = jwtAuthenticationToken.getClaims();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) claims.get(SecurityConstant.AUTHORITIES);
        String username = (String) claims.get("sub");
        String newToken = JwtUtil.generateToken(authorities, username);
        String oldToken = jwtAuthenticationToken.getToken();
        UserToken userToken = userTokenMapper.findByToken(oldToken);
        userToken.setToken(newToken);
        updateByPrimaryKeySelective(userToken);
        jwtAuthenticationToken.setToken(newToken);
        return newToken;
    }

}

