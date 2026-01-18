package io.github.timemachinelab.service.port.out;

import io.github.timemachinelab.domain.user.model.UserModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;

public interface UserRepositoryPort {
    UserModel saveByCredential(UserModel model);

    UserModel selectByCredential(VerifyCredentialModel model);
}
