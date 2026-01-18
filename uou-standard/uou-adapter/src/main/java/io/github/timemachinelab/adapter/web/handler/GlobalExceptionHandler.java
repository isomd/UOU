package io.github.timemachinelab.adapter.web.handler;

import io.github.timemachinelab.api.error.BusinessException;
import io.github.timemachinelab.common.resp.result.Result;
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
    public Result<?> handleBusiness(BusinessException ex) {
        log.warn("BusinessException: code={}, message={}", ex.getCode(), ex.getMessage());
        return Result.error(ex.getCode(), ex.getMessage());
    }

    /**
     * @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                       .stream()
                       .map(FieldError::getDefaultMessage)
                       .collect(Collectors.joining(", "));
        log.warn("MethodArgumentNotValidException: {}", msg);
        return Result.error("303", msg);
    }

    /**
     * @ModelAttribute / 表单参数校验失败
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBind(BindException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                       .stream()
                       .map(FieldError::getDefaultMessage)
                       .collect(Collectors.joining(", "));
        log.warn("BindException: {}", msg);
        return Result.error("303", msg);
    }

    /**
     * @PathVariable / @RequestParam 校验失败
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraint(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations()
                       .stream()
                       .map(ConstraintViolation::getMessage)
                       .collect(Collectors.joining(", "));
        log.warn("ConstraintViolationException: {}", msg);
        return Result.error("303", msg);
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleAll(Exception ex) {
        log.error("Unexpected error", ex);
        return Result.error("500", "服务器内部错误");
    }
}