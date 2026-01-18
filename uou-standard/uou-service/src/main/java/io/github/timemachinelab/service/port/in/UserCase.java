package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.domain.user.model.UserModel;
import io.github.timemachinelab.service.model.UserInfoModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;

public interface UserCase {
    UserInfoModel verifyCredential(VerifyCredentialModel verifyCredentialModel);
}