package io.github.timemachinelab.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyCredentialModel {
    // 用户登录凭证信息
    private String credentialAccount;  // 凭证账号
    private String credentialType;     // 凭证类型
    private String credentialContent;  // 凭证内容

    public VerifyCredentialModel(String credentialAccount, String credentialType, String credentialContent) {
        this.credentialAccount = credentialAccount;
        this.credentialType = credentialType;
        this.credentialContent = credentialContent;
    }
}
