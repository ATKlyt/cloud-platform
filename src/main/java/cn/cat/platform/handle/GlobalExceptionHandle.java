package cn.cat.platform.handle;

import cn.cat.platform.common.Result;
import cn.cat.platform.enums.ResultCode;
import cn.cat.platform.exception.BusinessException;
import cn.cat.platform.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author linlt
 * @createTime 2020/3/30 23:51
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return ResultUtil.error(businessException);
        } else {
            log.error("系统错误", e);
            // nullPointExe
            return ResultUtil.error(ResultCode.UNKNOWN_ERROR);
        }
    }
}
