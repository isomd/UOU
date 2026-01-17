package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.service.model.CreateUserModel;
import io.github.timemachinelab.service.model.UserModel;

public interface CreateUserCase {
    /**
     * 创建用户
     * @param createUserModel
     */
    UserModel createUser(CreateUserModel createUserModel);
}
