package cn.cat.platform.security.handle;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.Result;
import cn.cat.platform.common.SecurityConstant;
import cn.cat.platform.model.DO.Role;
import cn.cat.platform.model.DO.User;
import cn.cat.platform.model.VO.UserVO;
import cn.cat.platform.service.RoleService;
import cn.cat.platform.service.UserService;
import cn.cat.platform.service.UserTokenService;
import cn.cat.platform.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author linlt
 * @createTime 2020/4/3 13:46
 * @description 登录成功时的处理逻辑
 */
@Component
public class JwtLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //从SecurityContextHolder.getContext().getAuthentication()获取的authentication为null
        org.springframework.security.core.userdetails.User userDetails
                = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        //用户信息
        User user = userService.findByUsername(userDetails.getUsername());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String reservedInfo = user.getReservedInfo();
        List<String> reservedInfoList = new ArrayList<>(0);
        if (reservedInfo != null) {
            reservedInfoList = Arrays.asList(reservedInfo.split(Constant.delimiter));
        }
        userVO.setReservedInfoList(reservedInfoList);
        Role role = roleService.findByUserId(user.getUserId());
        User superiorUser = userService.findSuperiorUserByUserId(user.getUserId());

        Map<String, Object> data = new HashMap<>(4);
        data.put("user", userVO);
        data.put("role", role);
        data.put("superiorUser", superiorUser);

        //token
        String token = userTokenService.saveToken(userDetails, user.getUserId());

        //此处还可以进行一些处理，比登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //返回json数据
        Result result = ResultUtil.success(data);
        //为token设置header
        response.setHeader(SecurityConstant.TOKEN_HEADER, SecurityConstant.TOKEN_PREFIX + token);
        //处理编码方式，防止中文乱码的情况
        response.setContentType(SecurityConstant.CONTENT_TYPE_JSON);
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));

    }
}
