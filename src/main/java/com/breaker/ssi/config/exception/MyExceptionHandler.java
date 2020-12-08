package com.breaker.ssi.config.exception;

import com.breaker.ssi.utils.result.ResultEnums;
import com.breaker.ssi.utils.result.Ret;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Ret handle(Exception e) {
        e.printStackTrace();
        return new Ret(ResultEnums.ERROR);
//        return ResultUtil.build(ResultEnums.ERROR, e.toString());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Ret handleUpload(Exception e) {
        e.printStackTrace();
        System.out.println("文件超出最大限制");
        return new Ret(ResultEnums.UPLOAD_SIZE_EXCEED).setData("上传文件大小不能超过50M");
//        return ResultUtil.build(ResultEnums.ERROR, e.toString());
    }

}
