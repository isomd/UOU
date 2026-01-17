package io.github.timemachinelab.service.application;

import io.github.timemachinelab.service.model.UserModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.port.in.UserCase;
import io.github.timemachinelab.service.port.out.UserRepositoryPort;
import io.github.timemachinelab.domain.credential.service.CredentialVerificationService;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService implements UserCase {
    private final CredentialVerificationService credentialVerificationService;

    private final UserRepositoryPort userRepositoryPort;

    public UserApplicationService(CredentialVerificationService credentialVerificationService,
                                  UserRepositoryPort userRepositoryPort) {
        this.credentialVerificationService = credentialVerificationService;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserModel verifyCredential(VerifyCredentialModel verifyCredentialModel) {
        // 1. 将应用层的 DTO 转换为领域模型
        io.github.timemachinelab.domain.credential.model.VerifyCredentialModel domainModel = new io.github.timemachinelab.domain.credential.model.VerifyCredentialModel(
                verifyCredentialModel.getCredentialAccount(),
                verifyCredentialModel.getCredentialContent(),
                verifyCredentialModel.getCredentialType()
        );

        // 2. 调用领域服务进行凭证验证
        boolean isValid = credentialVerificationService.verify(domainModel);

        if (isValid) {
            return new VerifyCredentialResp(true, "Credential is valid.");
        } else {
            // 如果凭证不正确，创建新的凭证
            userRepositoryPort.save(verifyCredentialModel);  // 使用 DTO 保存数据
            return new VerifyCredentialResp(false, "Credential is invalid, new credential created.");
        }
    }
}
