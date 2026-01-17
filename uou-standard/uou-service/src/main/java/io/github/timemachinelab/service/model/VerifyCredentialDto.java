package io.github.timemachinelab.service.model;

public class VerifyCredentialDto {
    private String credentialAccount;
    private String credentialContent;
    private String credentialType;

    // 构造函数
    public VerifyCredentialDto(String credentialAccount, String credentialContent, String credentialType) {
        this.credentialAccount = credentialAccount;
        this.credentialContent = credentialContent;
        this.credentialType = credentialType;
    }

    // Getter 和 Setter
    public String getCredentialAccount() {
        return credentialAccount;
    }

    public void setCredentialAccount(String credentialAccount) {
        this.credentialAccount = credentialAccount;
    }

    public String getCredentialContent() {
        return credentialContent;
    }

    public void setCredentialContent(String credentialContent) {
        this.credentialContent = credentialContent;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }
}
