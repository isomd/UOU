package io.github.timemachinelab.domain.credential.model.enums;


/**
 * 领域层内置枚举 → 凭证类型（核心业务枚举）
 * 存放位置：tml-domain/enums/CredentialTypeEnum.java
 * 领域层核心业务规则直接使用，值和数据库字段完全匹配，无外部依赖
 */

public enum CredentialTypeEnum {
    PHONE("1", "手机号"),
    EMAIL("2", "邮箱"),
    USER_NAME("3", "用户名"),
    THIRD_PARTY_ID("4", "第三方账号(QQ/WX)"); // 仅该类型允许自动创建用户

    private final String code;
    private final String desc;

    CredentialTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 领域层业务规则：判断是否是允许自动创建用户的第三方账号
    public boolean isSupportAutoCreate() {
        return this == THIRD_PARTY_ID;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}