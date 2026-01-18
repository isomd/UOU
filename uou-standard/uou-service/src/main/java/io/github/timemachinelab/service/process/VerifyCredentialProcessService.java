package io.github.timemachinelab.service.process;

import io.github.timemachinelab.api.enums.CredentialTypeEnum;
import io.github.timemachinelab.service.model.UserInfoModel;
import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.service.port.in.UserCase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class VerifyCredentialProcessService {

    @Resource
    private UserCase userCase;

    // 业务流程编排：验证凭证 -> 如果凭证无效，创建新的凭证 -> 更新用户信息
    public UserInfoModel verifyCredential(VerifyCredentialModel verifyCredentialModel) {
        // 转换credentialType（desc → code，保持原值兜底）
        verifyCredentialModel.setCredentialType(
                Optional.ofNullable(CredentialTypeEnum.getByDesc(verifyCredentialModel.getCredentialType()))
                        .map(CredentialTypeEnum::getCode)
                        .orElse(verifyCredentialModel.getCredentialType()));

        // 在 process 层中，验证凭证，传递到 application 层
        return userCase.verifyCredential(verifyCredentialModel);
    }
}
