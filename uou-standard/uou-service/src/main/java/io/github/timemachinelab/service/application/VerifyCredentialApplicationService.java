package io.github.timemachinelab.service.application;


import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.domain.credential.model.VerifyCredentialModel;
import io.github.timemachinelab.domain.credential.service.CredentialVerificationService;
import io.github.timemachinelab.service.model.VerifyCredentialDto;
import io.github.timemachinelab.service.port.in.VerifyCredentialUseCase;
import io.github.timemachinelab.service.port.out.CredentialRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyCredentialApplicationService implements VerifyCredentialUseCase {

    @Autowired
    private  CredentialVerificationService credentialVerificationService;
    @Autowired
    private CredentialRepositoryPort credentialRepositoryPort;



    @Override
    public VerifyCredentialResp verifyCredential(VerifyCredentialDto verifyCredentialDto) {
        // 将 service 层的 DTO 转换为 domain 层的领域模型
        VerifyCredentialModel domainModel = new VerifyCredentialModel(
                verifyCredentialDto.getCredentialAccount(),
                verifyCredentialDto.getCredentialContent(),
                verifyCredentialDto.getCredentialType()
        );

        // 调用领域服务进行凭证验证
        boolean isValid = credentialVerificationService.verify(domainModel);

        if (isValid) {
            return new VerifyCredentialResp(true, "Credential is valid.");
        } else {
            // 如果凭证不正确，创建新的凭证
            credentialRepositoryPort.save(verifyCredentialDto); // 保存 DTO 数据
            return new VerifyCredentialResp(false, "Credential is invalid, new credential created.");
        }
    }
}
