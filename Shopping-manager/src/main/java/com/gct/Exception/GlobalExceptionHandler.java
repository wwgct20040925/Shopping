package com.gct.Exception;

import com.gct.pojo.vo.Result.Result;
import com.gct.pojo.vo.Result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e){
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.DATA_ERROR);
    }

    @ExceptionHandler(MyException.class)
    public Result MyException(MyException myException){
        if(myException.getResultCodeEnum()!=null&&myException.getMessage()!=null){
            return Result.fail(myException.getResultCodeEnum(),myException.getMessage());
        }
        else if (myException.getResultCodeEnum()!=null){
            return Result.fail(myException.getResultCodeEnum());
        }
        else {
            return Result.build(null,myException.getCode(),myException.getMessage());
        }
    }
}
