package io.github.timemachinelab.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoModel {
    private String userId;

    private String userStatus;
    // 用户登录相关凭证信息
    private String credentialAccount;

    private String credentialType;
    // 用户的一些信息

}
