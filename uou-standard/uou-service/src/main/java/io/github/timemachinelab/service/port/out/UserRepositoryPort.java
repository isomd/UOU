package io.github.timemachinelab.service.port.out;

import io.github.timemachinelab.service.model.UserModel;

public interface UserRepositoryPort {
    UserModel saveByCredential(UserModel model);

    UserModel selectByCredential(UserModel model);
}
