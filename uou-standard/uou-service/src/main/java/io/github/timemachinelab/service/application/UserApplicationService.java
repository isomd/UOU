package io.github.timemachinelab.service.application;

import io.github.timemachinelab.api.enums.CredentialTypeEnum;
import io.github.timemachinelab.api.error.BusinessException;
import io.github.timemachinelab.api.error.ErrorCode;
import io.github.timemachinelab.domain.user.model.UserModel;
import io.github.timemachinelab.service.model.UserInfoModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.port.in.UserCase;
import io.github.timemachinelab.service.port.out.UserRepositoryPort;
import io.github.timemachinelab.domain.user.service.UserDomainService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserApplicationService implements UserCase {
    private final UserDomainService userDomainService;

    private final UserRepositoryPort userRepositoryPort;

    public UserApplicationService(UserDomainService userDomainService,
                                  UserRepositoryPort userRepositoryPort) {
        this.userDomainService = userDomainService;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserInfoModel verifyCredential(VerifyCredentialModel verifyCredentialModel) {
        // 1. 根据凭证信息查询用户信息
        UserModel userModel = userRepositoryPort.selectByCredential(verifyCredentialModel);

        // 2.verifyCredentialModel转化为userModel
        UserModel verifyUserModel = UserModel.builder()
                .credentialAccount(verifyCredentialModel.getCredentialAccount())
                .credentialType(verifyCredentialModel.getCredentialType())
                .credentialContent(verifyCredentialModel.getCredentialContent())
                .build();

        // 3. 调用领域服务进行凭证验证
        boolean isValid = userDomainService.verifyCredential(verifyUserModel, userModel);

        // 4. 验证通过
        if (isValid){
            return convertToUserModelInfo(userModel);
        } else {
            CredentialTypeEnum byCode = CredentialTypeEnum.getByCode(verifyUserModel.getCredentialType());

            if (byCode != null && byCode.isSupportAutoCreate()){
                try {
                    UserModel newUserModel = userRepositoryPort.saveByCredential(verifyUserModel);
                    return convertToUserModelInfo(newUserModel);
                } catch (Exception e){
                    log.error("保存用户信息失败: {}", e.getMessage());
                    throw new BusinessException("500", "保存用户信息失败");
                }

            }
        }

        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    private UserInfoModel convertToUserModelInfo(UserModel userModel){
        if (userModel == null){
            return null;
        }

        return UserInfoModel.builder()
                .userId(userModel.getUserId())
                .userStatus(userModel.getUserStatus())
                .credentialAccount(userModel.getCredentialAccount())
                .credentialType(userModel.getCredentialType())
                .build();
    }
}
