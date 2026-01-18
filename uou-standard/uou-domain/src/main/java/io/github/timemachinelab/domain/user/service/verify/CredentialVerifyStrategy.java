package io.github.timemachinelab.domain.user.service.verify;


import io.github.timemachinelab.api.enums.CredentialTypeEnum;
import io.github.timemachinelab.domain.user.model.UserModel;

public interface CredentialVerifyStrategy {
    boolean verify(UserModel verifyUserModel, UserModel userModel);

    CredentialTypeEnum getCredentialType();
}
