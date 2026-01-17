package io.github.timemachinelab.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    USER_CREATION_FAILED(1003, "用户创建失败"),
    USER_UPDATION_FAILED(1004, "用户更新失败"),
    USER_DELETION_FAILED(1005, "用户删除失败"),
    USER_GET_FAILED(1006, "用户获取失败"),
    USER_LOGIN_FAILED(1007, "用户登录失败");

    private final Integer code;

    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
