package com.demo.mall.vo;

import com.demo.mall.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @author wucong
 * @date 2020/11/5 19:44
 * @description com.demo.mall.vo
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    private Integer status;

    private String msg;

    private T data;

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseVo<T> successByMsg(String msg) {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseVo<T> success() {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum) {
        return new ResponseVo(responseEnum.getCode(), responseEnum.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVo(responseEnum.getCode(), msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVo(responseEnum.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getField()
                        + " " + bindingResult.getFieldError().getDefaultMessage());
    }
}
