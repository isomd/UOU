package io.github.timemachinelab.service.port.in;

import io.github.timemachinelab.service.model.CreateUserModel;
import io.github.timemachinelab.service.model.GetUserModel;
import io.github.timemachinelab.service.model.UserModel;

public interface UserCase {
    /**
     * 获取用户
     * @param getUserModel
     */
    UserModel getUser(GetUserModel getUserModel);

    /**
     * 创建用户
     * @param createUserModel
     */
    UserModel createUser(CreateUserModel createUserModel);
}
