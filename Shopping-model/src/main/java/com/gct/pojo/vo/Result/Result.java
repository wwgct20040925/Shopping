package com.gct.pojo.vo.Result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "响应结果实体类")
public class Result<T> {

    //返回码
    @Schema(description = "业务状态码")
    private Integer code;

    //返回消息
    @Schema(description = "响应消息")
    private String message;

    //返回数据
    @Schema(description = "业务数据")
    private T data;

    // 私有化构造
    private Result() {}

    // 返回数据
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setData(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 通过枚举构造Result对象
    public static <T> Result build(T body , ResultCodeEnum resultCodeEnum) {
        return build(body , resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }
    //构建成功的消息，code:200，message:操作成功  Data:自定义
    public static <T> Result<T> success(T data) {
        Result<T> resultData = new Result<>();
        resultData.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultData.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        resultData.setData(data);
        return resultData;
    }

    //构建失败的消息，code:通过ResultCodeEnum的枚举类获取，message:通过ResultCodeEnum的枚举类获取
    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        Result<T> resultData = new Result<>();
        resultData.setCode(resultCodeEnum.getCode());
        resultData.setMessage(resultCodeEnum.getMessage());
        return resultData;
    }

    //构建失败的消息，code:通过ResultCodeEnum的枚举类获取，message:自定义
    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum,String message) {
        Result<T> resultData = new Result<>();
        resultData.setCode(resultCodeEnum.getCode());
        resultData.setMessage(message);
        return resultData;
    }

}
