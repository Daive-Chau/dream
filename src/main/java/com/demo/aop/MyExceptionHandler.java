package com.demo.aop;

import com.demo.Response.ResultMessage;
import com.demo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * controller全局异常处理
 * @author 43291
 */
@ControllerAdvice
public class MyExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultMessage exceptionHandler(Exception e) {
        logger.error("Controller Exception: ", e);
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            //业务异常处理
            return new ResultMessage(false, be.getMessage(), be.getData());
        } else {
            //未料异常处理
            return new ResultMessage(false, e.getMessage());
        }
    }
}
