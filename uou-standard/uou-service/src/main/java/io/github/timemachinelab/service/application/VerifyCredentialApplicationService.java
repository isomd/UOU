package io.github.timemachinelab.service.application;

import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.port.in.VerifyCredentialUseCase;
import io.github.timemachinelab.service.port.out.CredentialRepositoryPort;
import io.github.timemachinelab.domain.credential.service.CredentialVerificationService;
import org.springframework.stereotype.Service;

@Service
public class VerifyCredentialApplicationService implements VerifyCredentialUseCase {

    private final CredentialVerificationService credentialVerificationService;
    private final CredentialRepositoryPort credentialRepositoryPort;

    public VerifyCredentialApplicationService(CredentialVerificationService credentialVerificationService,
                                              CredentialRepositoryPort credentialRepositoryPort) {
        this.credentialVerificationService = credentialVerificationService;
        this.credentialRepositoryPort = credentialRepositoryPort;
    }

    @Override
    public VerifyCredentialResp verifyCredential(VerifyCredentialModel verifyCredentialDto) {
        // 1. 将应用层的 DTO 转换为领域模型
        io.github.timemachinelab.domain.credential.model.VerifyCredentialModel domainModel = new io.github.timemachinelab.domain.credential.model.VerifyCredentialModel(
                verifyCredentialDto.getCredentialAccount(),
                verifyCredentialDto.getCredentialContent(),
                verifyCredentialDto.getCredentialType()
        );

        // 2. 调用领域服务进行凭证验证
        boolean isValid = credentialVerificationService.verify(domainModel);

        if (isValid) {
            return new VerifyCredentialResp(true, "Credential is valid.");
        } else {
            // 如果凭证不正确，创建新的凭证
            credentialRepositoryPort.save(verifyCredentialDto);  // 使用 DTO 保存数据
            return new VerifyCredentialResp(false, "Credential is invalid, new credential created.");
        }
    }
}
