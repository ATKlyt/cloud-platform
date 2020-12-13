package cn.cat.platform.security.handle;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linlt
 * @createTime 2020/4/3 19:43
 * @description 会话信息过期策略
 *
 * 当账号异地登录导致被挤下线时也要返回给前端json格式的数据，
 * 比如提示"账号下线"或者"您的账号在异地登录,可能由于密码泄露，建议修改密码"等。
 * 这时就要实现SessionInformationExpiredStrategy（会话信息过期策略）来自定义会话过期时的处理逻辑。
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy{
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Result result = ResultUtil.error(ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
