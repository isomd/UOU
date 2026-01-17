package io.github.timemachinelab.service.model;

public class VerifyCredentialModel {
    private String credentialAccount;  // 凭证账号
    private String credentialType;     // 凭证类型
    private String credentialContent;  // 凭证内容

    // 用户信息
    private String nickname;          // 昵称
    private String avatar;            // 头像

    // 构造函数、Getter 和 Setter
    public VerifyCredentialModel(String credentialAccount, String credentialType, String credentialContent,
                                 String nickname, String avatar) {
        this.credentialAccount = credentialAccount;
        this.credentialType = credentialType;
        this.credentialContent = credentialContent;
        this.nickname = nickname;
        this.avatar = avatar;
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

    // 用户信息 Getters 和 Setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
