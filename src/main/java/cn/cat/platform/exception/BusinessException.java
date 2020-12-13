package cn.cat.platform.exception;

import cn.cat.platform.enums.ResultCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @author linlt
 * @CreateTime 2020/3/20 12:56
 */
@Getter @Setter
public class BusinessException extends RuntimeException {


    private ResultCode resultCode;

    /**
     * 额外的错误提示信息
     */
    private String extraMessage;

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String extraMessage) {
        this.extraMessage = extraMessage;
        this.resultCode = resultCode;
    }


    /**
     * 重写fillInStackTrace方法会使得这个自定义的异常不会收集线程的整个异常栈信息，会大大提高减少异常开销。
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}

