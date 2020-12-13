package cn.cat.platform.aspect;

import cn.cat.platform.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linlt
 * @createTime 2020/3/30 23:13
 */
@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Pointcut("execution(public * cn.cat.platform.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        log.info("url={}", request.getRequestURL());
        log.info("method={}", request.getMethod());
        log.info("ip={}", request.getRemoteAddr());
        log.info("classMethod={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("args={}", joinPoint.getArgs());
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Result result){
        if (result != null && result.getData() != null){
            log.info("response={}", result.getData().toString());
        }
    }


}
