package io.github.timemachinelab.api.resp;

public class VerifyCredentialResp {
    private boolean isValid;    // 是否验证通过
    private String message;     // 响应信息

    // 构造函数、Getter 和 Setter
    public VerifyCredentialResp(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

