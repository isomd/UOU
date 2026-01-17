package io.github.timemachinelab.service.process;

import io.github.timemachinelab.service.model.CreateUserModel;
import io.github.timemachinelab.service.model.GetUserModel;
import io.github.timemachinelab.service.model.UserModel;
import io.github.timemachinelab.service.port.in.CreateUserCase;
import io.github.timemachinelab.service.port.in.UserCase;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProcessService {

    @Resource
    private UserCase userCase;
    
    /**
     * 获取用户
     * @param getUserModel
     * @return
     */
    public UserModel getUser(GetUserModel getUserModel){
        UserModel userModel = userCase.getUser(getUserModel);
        
        if(userModel == null){
            // 根据当前信息和凭证选择性创建用户信息
            CreateUserModel createUserModel = CreateUserModel.builder()
                    .userName(getUserModel.getUserName())
                    .password(getUserModel.getPassword())
                    .build();
            userModel = userCase.createUser(createUserModel);
        }

        return userModel;
    }
}
