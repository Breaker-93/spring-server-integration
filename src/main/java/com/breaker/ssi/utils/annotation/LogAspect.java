package com.breaker.ssi.utils.annotation;

import com.breaker.ssi.sys.entity.SysLog;
import com.breaker.ssi.sys.mapper.SysLogMapper;
import com.breaker.ssi.sys.service.ISysLogService;
import com.breaker.ssi.utils.IpAddressUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class LogAspect {
    @Autowired
    ISysLogService sysLogService;

    @Pointcut("@annotation(com.breaker.ssi.utils.annotation.OperationLog)")
    public void logPointCut() {
    }

    @AfterReturning("logPointCut()")
    public void afterReturn(JoinPoint joinPoint) {
        saveLog(joinPoint, null);
    }

    @AfterThrowing(value="logPointCut()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Throwable e) {
        saveLog(joinPoint, e);
    }

    public void saveLog(JoinPoint joinPoint, Throwable e) {
        SysLog sysLog = new SysLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        // 获取注解操作
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            String value = operationLog.value();
            sysLog.setOperateContent(value);//保存获取的操作
        }
        if(e != null) {
            String exceptionMes = e.getMessage().toString();
            if(exceptionMes.length() > 300) {
                exceptionMes = exceptionMes.substring(0, 300);
            }
            sysLog.setFailReason(exceptionMes);
        }

        //获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            sysLog.setOperatorId((String)authentication.getPrincipal());
        }

        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        sysLog.setOperateIp(IpAddressUtil.getIpAdrress(request));

        //请求的时间
        sysLog.setOperateTime(LocalDateTime.now());

        sysLogService.save(sysLog);
        //获取请求的类名
//        String className = joinPoint.getTarget().getClass().getName();

        //获取请求的方法名
//        String methodName = method.getName();
//        sysLog.setMethod(className + "." + methodName);

        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        //将参数所在的数组转换成json
//        String params = null;
//        try {
//            params = JsonUtil.obj2json(args);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sysLog.setParams(params);




    }
}
