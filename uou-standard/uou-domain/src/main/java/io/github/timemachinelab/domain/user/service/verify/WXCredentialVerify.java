package io.github.timemachinelab.domain.user.service.verify;

import io.github.timemachinelab.api.enums.CredentialTypeEnum;

public class WXCredentialVerify extends ThirdCredentialVerifyStrategy{
    @Override
    public CredentialTypeEnum getCredentialType() {
        return CredentialTypeEnum.WX;
    }
}
