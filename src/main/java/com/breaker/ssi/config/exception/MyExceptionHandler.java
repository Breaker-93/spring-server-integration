package com.breaker.ssi.config.exception;

import com.breaker.ssi.utils.result.ResultEnums;
import com.breaker.ssi.utils.result.Ret;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Ret handle(Exception e) {
        e.printStackTrace();
        return new Ret(ResultEnums.ERROR);
//        return ResultUtil.build(ResultEnums.ERROR, e.toString());
    }

}
