package com.demo.mall.exception;

import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author wucong
 * @date 2020/11/5 21:33
 * @description 全局异常处理
 */
@RestControllerAdvice
public class RuntimeExceptionHandler {

    /**
     * 普通运行时异常处理
     * @param e
     * @return
     */
   // @ResponseStatus(HttpStatus.FORBIDDEN)
   @ExceptionHandler(value = {RuntimeException.class})
    public ResponseVo handler(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }

    /**
     * 参数校验异常进行统一异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseVo notValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseVo.error(ResponseEnum.PARAM_ERROR, e.getBindingResult());
    }

}
