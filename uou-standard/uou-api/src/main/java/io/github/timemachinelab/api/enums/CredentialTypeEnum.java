package io.github.timemachinelab.api.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 领域层内置枚举 → 凭证类型（核心业务枚举）
 * 存放位置：tml-domain/enums/CredentialTypeEnum.java
 * 领域层核心业务规则直接使用，值和数据库字段完全匹配，无外部依赖
 */

@Getter
@AllArgsConstructor
public enum CredentialTypeEnum {
    PHONE("1", "手机号", false),
    EMAIL("2", "邮箱", false),
    USER_NAME("3", "用户名", false),
    QQ("4", "QQ", true),
    WX("5", "微信", true),; // 仅该类型允许自动创建用户

    private final String code;

    private final String desc;

    private final boolean isSupportAutoCreate;

    public static CredentialTypeEnum getByCode(String code) {
        for (CredentialTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static CredentialTypeEnum getByDesc(String desc) {
        for (CredentialTypeEnum value : values()) {
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        return null;
    }
}