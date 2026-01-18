package io.github.timemachinelab.api.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private String code;

    private String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
