package io.github.timemachinelab.domain.credential.model;

import lombok.Data;

@Data
public class VerifyCredentialModel {
    private String credentialAccount;
    private String credentialContent;
    private String credentialType;

    // 构造函数
    public VerifyCredentialModel(String credentialAccount, String credentialContent, String credentialType) {
        this.credentialAccount = credentialAccount;
        this.credentialContent = credentialContent;
        this.credentialType = credentialType;
    }
}
