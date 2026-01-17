package io.github.timemachinelab.api.req;

public class VerifyCredentialReq {
    private String credentialAccount;  // 凭证账号，例如手机号、邮箱等
    private String credentialType;     // 凭证类型（1: 手机号, 2: 邮箱, 3: 用户名, 4: 第三方ID）
    private String credentialContent;  // 凭证内容（如加密后的密码）

    // 构造函数、Getter 和 Setter
    public VerifyCredentialReq(String credentialAccount, String credentialType, String credentialContent) {
        this.credentialAccount = credentialAccount;
        this.credentialType = credentialType;
        this.credentialContent = credentialContent;
    }

    // Getters 和 Setters
    public String getCredentialAccount() {
        return credentialAccount;
    }

    public void setCredentialAccount(String credentialAccount) {
        this.credentialAccount = credentialAccount;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialContent() {
        return credentialContent;
    }

    public void setCredentialContent(String credentialContent) {
        this.credentialContent = credentialContent;
    }
}
