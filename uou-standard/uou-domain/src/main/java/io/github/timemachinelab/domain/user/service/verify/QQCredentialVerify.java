package io.github.timemachinelab.domain.user.service.verify;

import io.github.timemachinelab.api.enums.CredentialTypeEnum;
import io.github.timemachinelab.domain.user.model.UserModel;

public class QQCredentialVerify extends ThirdCredentialVerifyStrategy {

    @Override
    public CredentialTypeEnum getCredentialType() {
        return CredentialTypeEnum.QQ;
    }

}
