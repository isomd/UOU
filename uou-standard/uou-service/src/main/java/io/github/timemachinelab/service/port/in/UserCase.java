package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.service.model.UserModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;

public interface UserCase {
    UserModel verifyCredential(VerifyCredentialModel verifyCredentialModel);
}