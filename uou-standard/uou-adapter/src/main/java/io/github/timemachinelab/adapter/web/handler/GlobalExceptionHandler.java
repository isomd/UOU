package io.github.timemachinelab.adapter.web.handler;

import io.github.timemachinelab.api.error.BusinessException;
import io.github.timemachinelab.api.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResp<Void> handleBusiness(BusinessException ex) {
        log.warn("BusinessException: code={}, message={}", ex.getCode(), ex.getMessage());
        return CommonResp.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResp<Void> handleValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                       .stream()
                       .map(FieldError::getDefaultMessage)
                       .collect(Collectors.joining(", "));
        log.warn("MethodArgumentNotValidException: {}", msg);
        return CommonResp.fail(400, msg);
    }

    /**
     * @ModelAttribute / 表单参数校验失败
     */
    @ExceptionHandler(BindException.class)
    public CommonResp<Void> handleBind(BindException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                       .stream()
                       .map(FieldError::getDefaultMessage)
                       .collect(Collectors.joining(", "));
        log.warn("BindException: {}", msg);
        return CommonResp.fail(400, msg);
    }

    /**
     * @PathVariable / @RequestParam 校验失败
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResp<Void> handleConstraint(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations()
                       .stream()
                       .map(ConstraintViolation::getMessage)
                       .collect(Collectors.joining(", "));
        log.warn("ConstraintViolationException: {}", msg);
        return CommonResp.fail(400, msg);
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResp<Void> handleAll(Exception ex) {
        log.error("Unexpected error", ex);
        return CommonResp.fail(500, "服务器内部错误");
    }
}