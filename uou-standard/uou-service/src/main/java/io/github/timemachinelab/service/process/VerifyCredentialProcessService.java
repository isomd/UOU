package io.github.timemachinelab.service.process;

import io.github.timemachinelab.service.model.VerifyCredentialModel;
import io.github.timemachinelab.api.resp.VerifyCredentialResp;
import io.github.timemachinelab.service.application.VerifyCredentialApplicationService;
import org.springframework.stereotype.Service;

@Service
public class VerifyCredentialProcessService {

    private final VerifyCredentialApplicationService verifyCredentialApplicationService;

    public VerifyCredentialProcessService(VerifyCredentialApplicationService verifyCredentialApplicationService) {
        this.verifyCredentialApplicationService = verifyCredentialApplicationService;
    }

    // 业务流程编排：验证凭证 -> 如果凭证无效，创建新的凭证 -> 更新用户信息
    public VerifyCredentialResp process(VerifyCredentialModel verifyCredentialDto) {
        // 在 process 层中，验证凭证，传递到 application 层
        return verifyCredentialApplicationService.verifyCredential(verifyCredentialDto);
    }
}
