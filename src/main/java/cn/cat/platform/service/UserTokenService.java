package cn.cat.platform.service;

import cn.cat.platform.model.DO.UserToken;
import cn.cat.platform.security.token.JwtAuthenticationToken;
import org.springframework.security.core.userdetails.User;

/**
 * @author linlt
 * @createTime 2020/4/5 15:04
 * @description TODO
 */
public interface UserTokenService {


    int deleteByPrimaryKey(Integer tokenId);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Integer tokenId);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);

    String saveToken(User userDetails, Integer userId);

    UserToken getToken(Integer userId);

    void clearToken(String username);

    String refreshToken(JwtAuthenticationToken jwtAuthenticationToken);
}

