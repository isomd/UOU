package io.github.timemachinelab.api.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCredentialReq {
    private String credentialAccount;  // 凭证账号，例如手机号、邮箱等
    private String credentialType;     // 凭证类型（1: 手机号, 2: 邮箱, 3: 用户名, 4: QQ， 5：WX）
    private String credentialContent;  // 凭证内容（如加密后的密码）

    // 一些额外信息

}
